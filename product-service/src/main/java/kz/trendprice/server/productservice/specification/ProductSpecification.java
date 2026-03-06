package kz.trendprice.server.productservice.specification;

import kz.trendprice.server.productservice.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasTitle(String title) {
        return ((root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"
                )
        );
    }

    public static Specification<Product> hasCategory(String category) {
        return ((root, query, criteriaBuilder) ->
                category == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("categories").get("title")), category.toLowerCase()
                )
        );
    }

    public static Specification<Product> hasBrand(String brand) {
        return ((root, query, criteriaBuilder) ->
                brand == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("brand").get("title")), "%" + brand.toLowerCase() + "%"
                )
        );
    }

    public static Specification<Product> hasType(String type) {
        return ((root, query, criteriaBuilder) ->
                type == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("type")), "%" + type.toLowerCase() + "%"
                )
        );
    }
}
