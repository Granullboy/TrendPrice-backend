package kz.trendprice.server.parserservice.util;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DownloadJSON {

    private final String name;
    private final String link;
    private final Path folder;

    public DownloadJSON(final String name, final String link) {
        this.name = name;
        this.link = link;
        this.folder = Path.of("storage", "static");
    }

    public DownloadJSON(String name, String link, String folderPath) {
        this.name = name;
        this.link = link;
        this.folder = Path.of(folderPath);
    }

    public Path download() {
        try {
            Files.createDirectories(folder);

            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String filename = normalizeName(name) + "_" + date + ".json";
            Path filePath = folder.resolve(filename);

            if (Files.exists(filePath)) {
                return filePath;
            }

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(link))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            if (response.statusCode() < 200 || response.statusCode() > 299) {
                throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
            }

            Files.writeString(filePath, response.body(), StandardCharsets.UTF_8);

            return filePath;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to download JSON file", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to download JSON file", e);
        }
    }

    private String normalizeName(String value) {
        return value.trim()
                .toLowerCase()
                .replaceAll("[^a-zA-Z0-9а-яА-Я_-]", "_");
    }
}
