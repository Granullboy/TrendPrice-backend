package kz.trendprice.server.parserservice.parser.magnum;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.trendprice.server.parserservice.client.PriceClient;
import kz.trendprice.server.parserservice.client.ProductClient;
import kz.trendprice.server.parserservice.client.StoreClient;
import kz.trendprice.server.parserservice.dto.prices.PriceDto;
import kz.trendprice.server.parserservice.dto.products.BrandDto;
import kz.trendprice.server.parserservice.dto.products.ProductDto;
import kz.trendprice.server.parserservice.dto.stores.StoreDto;
import kz.trendprice.server.parserservice.mapping.entity.StoreProductMapping;
import kz.trendprice.server.parserservice.mapping.service.ProductMappingResolver;
import kz.trendprice.server.parserservice.mapping.service.StoreProductMappingService;
import kz.trendprice.server.parserservice.util.DownloadJSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class MagnumParser {

    private static final Pattern UNIT_PATTERN = Pattern.compile(
            "(?:(\\d+(?:[,.]\\d+)?)\\s*)?(МЛ|ГР|Г|КГ|Л|ШТ|ПАК)\\s*$",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
    );
    private static final Pattern BRAND_PATTERN = Pattern.compile("«([^»]+)»");

    private final ObjectMapper objectMapper;
    private final StoreClient storeClient;
    private final ProductClient productClient;
    private final PriceClient priceClient;
    private final ProductMappingResolver productMappingResolver;
    private final StoreProductMappingService storeProductMappingService;

    @Value("${parser.magnum.url}")
    private String url;

    @Value("${parser.magnum.file-name:magnum_products}")
    private String fileName;

    @Value("${parser.magnum.store-title:Magnum}")
    private String storeTitle;

    @Value("${parser.magnum.city:Almaty}")
    private String city;

    @Value("${parser.magnum.folder:storage/static}")
    private String folder;

    @Scheduled(cron = "${parser.magnum.cron}", zone = "${parser.magnum.zone}")
    public void runDaily() {
        log.info("Magnum parser scheduled run started");
        run();
        log.info("Magnum parser scheduled run finished");
    }

    public void run() {
        Path filePath = new DownloadJSON(fileName, url, folder).download();

        List<MagnumProductItem> items = readItems(filePath);
        StoreDto store = getStore();

        UUID storeUuid = UUID.fromString(store.id());
        Instant time = Instant.now();

        List<PriceDto> newPrices = new ArrayList<>();
        List<ProductDto> newProducts = new ArrayList<>();
        List<MagnumProductItem> newProductItems = new ArrayList<>();

        for (MagnumProductItem item : items) {
            String productId = productMappingResolver.resolveProductId(storeUuid, item.externalProductId());

            if ("-1".equals(productId)) {
                newProducts.add(toProductDto(item));
                newProductItems.add(item);
            } else {
                newPrices.add(toPriceDto(item, productId, store.id(), time));
            }
        }

        List<PriceDto> pricesFromCreatedProducts = createNewProductsAndPrices(
                storeUuid,
                store.id(),
                newProducts,
                newProductItems,
                time
        );

        List<PriceDto> allPrices = new ArrayList<>();
        allPrices.addAll(newPrices);
        allPrices.addAll(pricesFromCreatedProducts);

        if (!allPrices.isEmpty()) {
            priceClient.massCreate(allPrices).block();
        }
    }

    private StoreDto getStore() {
        List<StoreDto> stores = storeClient.searchStores(storeTitle).block();

        if (stores == null || stores.isEmpty()) {
            throw new RuntimeException("Store not found: " + storeTitle);
        }

        return stores.get(0);
    }

    private List<MagnumProductItem> readItems(Path filePath) {
        try {
            JsonNode root = objectMapper.readTree(filePath.toFile());
            JsonNode data = root.path("data");

            List<MagnumProductItem> items = new ArrayList<>();

            if (!data.isArray()) {
                return items;
            }

            for (JsonNode node : data) {
                JsonNode attributes = node.path("attributes");

                items.add(new MagnumProductItem(
                        node.path("id").asText(),
                        attributes.path("name").asText(),
                        toBigDecimal(attributes.path("start_price")),
                        toBigDecimal(attributes.path("final_price")),
                        toBigDecimal(attributes.path("discount"))
                ));
            }

            return items;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Magnum JSON file", e);
        }
    }

    private List<PriceDto> createNewProductsAndPrices(
            UUID storeUuid,
            String storeId,
            List<ProductDto> newProducts,
            List<MagnumProductItem> newProductItems,
            Instant time
    ) {
        List<PriceDto> prices = new ArrayList<>();

        if (newProducts.isEmpty()) {
            return prices;
        }

        List<ProductDto> createdProducts = productClient.massCreate(newProducts).block();

        if (createdProducts == null || createdProducts.isEmpty()) {
            return prices;
        }

        int limit = Math.min(createdProducts.size(), newProductItems.size());

        for (int i = 0; i < limit; i++) {
            ProductDto createdProduct = createdProducts.get(i);
            MagnumProductItem item = newProductItems.get(i);

            if (createdProduct.id() == null) {
                continue;
            }

            UUID productUuid = UUID.fromString(createdProduct.id());

            StoreProductMapping mapping = new StoreProductMapping(
                    null,
                    storeUuid,
                    item.externalProductId(),
                    productUuid,
                    null,
                    null
            );

            storeProductMappingService.saveStoreProductMapping(mapping);

            prices.add(toPriceDto(item, createdProduct.id(), storeId, time));
        }

        return prices;
    }

    private ProductDto toProductDto(MagnumProductItem item) {
        ProductNameInfo info = parseName(item.name());

        BrandDto brand = null;

        if (info.brandTitle() != null && !info.brandTitle().isBlank()) {
            brand = new BrandDto(
                    null,
                    info.brandTitle(),
                    null,
                    null,
                    null
            );
        }

        return new ProductDto(
                null,
                info.title(),
                info.unit(),
                null, /*brand,*/
                null,
                List.of(),
                null,
                null
        );
    }

    private PriceDto toPriceDto(MagnumProductItem item, String productId, String storeId, Instant time) {
        ProductNameInfo info = parseName(item.name());

        return new PriceDto(
                null,
                productId,
                storeId,
                info.unitAmount(),
                info.unit(),
                item.startPrice(),
                "₸",
                city,
                item.discount(),
                item.finalPrice(),
                time,
                null,
                null
        );
    }

    private ProductNameInfo parseName(String value) {
        String source = value == null ? "" : value.trim();

        Matcher unitMatcher = UNIT_PATTERN.matcher(source);

        BigDecimal unitAmount = null;
        String unit = null;
        String titleSource = source;

        if (unitMatcher.find()) {
            String amount = unitMatcher.group(1);
            String rawUnit = unitMatcher.group(2).toUpperCase(Locale.ROOT);

            if (amount != null && !amount.isBlank()) {
                unitAmount = new BigDecimal(amount.replace(",", "."));
            } else {
                unitAmount = BigDecimal.ONE;
            }

            unit = normalizeUnit(rawUnit);
            titleSource = source.substring(0, unitMatcher.start()).trim();
        }
        else {
            unitAmount = BigDecimal.ONE;
            unit = normalizeUnit(null);
        }

        Matcher brandMatcher = BRAND_PATTERN.matcher(source);

        String brandTitle = null;

        if (brandMatcher.find()) {
            brandTitle = brandMatcher.group(1).trim();
        }

        String title = titleSource
                .replaceAll("(?iu)\\s*В\\s+АССОРТИМЕНТЕ\\s*", " ")
                .replaceAll("\\s+", " ")
                .trim();

        return new ProductNameInfo(
                title,
                brandTitle,
                unitAmount,
                unit
        );
    }

    private String normalizeUnit(String value) {
        if (value == null || value.isBlank()) {
            return "УП";
        }

        String unit = value.toUpperCase(Locale.ROOT);

        if ("МЛ".equals(unit)
                || "Г".equals(unit)
                || "КГ".equals(unit)
                || "Л".equals(unit)
                || "ПАК".equals(unit)
                || "ШТ".equals(unit)) {
            return unit;
        }
        if ("ГР".equals(unit)) {
            return "Г";
        }

        return "УП";
    }

    private BigDecimal toBigDecimal(JsonNode node) {
        if (node == null || node.isMissingNode() || node.isNull()) {
            return BigDecimal.ZERO;
        }

        if (node.isNumber()) {
            return node.decimalValue();
        }

        String value = node.asText();

        if (value == null || value.isBlank()) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(value.replace(",", "."));
    }

    private record MagnumProductItem(
            String externalProductId,
            String name,
            BigDecimal startPrice,
            BigDecimal finalPrice,
            BigDecimal discount
    ) {}

    private record ProductNameInfo(
            String title,
            String brandTitle,
            BigDecimal unitAmount,
            String unit
    ) {}
}