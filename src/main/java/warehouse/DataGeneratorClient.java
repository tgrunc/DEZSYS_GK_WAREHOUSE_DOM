package warehouse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class DataGeneratorClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Starting Data Generator Client...");
        
        try {
            // Generate and send Warehouse 1 data
            String warehouse1Id = "WH-" + UUID.randomUUID().toString();
            String warehouse1Json = createWarehouseJson(warehouse1Id, "Wien Hauptbahnhof", 1010, "Wien");
            sendPostRequest("/warehouse", warehouse1Json);
            
            // Add products to Warehouse 1
            for (int i = 0; i < 5; i++) {
                String productJson = createProductRequestJson(warehouse1Id, "P-" + UUID.randomUUID().toString().substring(0,8), "Energy Drink " + i, "Getraenk", random.nextInt(1000) + 100);
                sendPostRequest("/product", productJson);
            }

            // Generate and send Warehouse 2 data
            String warehouse2Id = "WH-" + UUID.randomUUID().toString();
            String warehouse2Json = createWarehouseJson(warehouse2Id, "Graz City", 8010, "Graz");
            sendPostRequest("/warehouse", warehouse2Json);
            
            // Add products to Warehouse 2
            for (int i = 0; i < 3; i++) {
                String productJson = createProductRequestJson(warehouse2Id, "P-" + UUID.randomUUID().toString().substring(0,8), "Waschmittel " + i, "Waschmittel", random.nextInt(500) + 50);
                sendPostRequest("/product", productJson);
            }
            
            System.out.println("Data generation completed successfully.");

        } catch (Exception e) {
            System.err.println("Error generating data: " + e.getMessage());
            System.err.println("Ensure the Spring Boot application is running on port 8080.");
        }
    }

    private static String createWarehouseJson(String id, String name, int zip, String city) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return String.format(
            "{\"warehouseID\":\"%s\",\"warehouseName\":\"%s\",\"timestamp\":\"%s\",\"warehousePostalCode\":%d,\"warehouseCity\":\"%s\",\"warehouseCountry\":\"Austria\",\"productData\":[]}",
            id, name, timestamp, zip, city
        );
    }

    private static String createProductRequestJson(String warehouseId, String productId, String name, String category, int quantity) {
        return String.format(
            "{\"warehouseID\":\"%s\",\"product\":{\"productID\":\"%s\",\"productName\":\"%s\",\"productCategory\":\"%s\",\"productQuantity\":%d}}",
            warehouseId, productId, name, category, quantity
        );
    }

    private static void sendPostRequest(String endpoint, String jsonBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        
        System.out.println("Sending POST to " + BASE_URL + endpoint);
        String response = restTemplate.postForObject(BASE_URL + endpoint, request, String.class);
        System.out.println("Response: " + response + "\n");
    }
}
