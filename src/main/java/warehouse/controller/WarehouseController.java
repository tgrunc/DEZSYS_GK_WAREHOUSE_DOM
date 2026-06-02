package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import warehouse.model.ProductData;
import warehouse.model.Warehouse;
import warehouse.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class WarehouseController {

    @Autowired
    private WarehouseRepository repository;

    // --- Warehouse Endpoints ---

    @PostMapping("/warehouse")
    public Warehouse addWarehouse(@RequestBody Warehouse warehouse) {
        return repository.save(warehouse);
    }

    @GetMapping("/warehouse")
    public List<Warehouse> getAllWarehouses() {
        return repository.findAll();
    }

    @GetMapping("/warehouse/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable String id) {
        Optional<Warehouse> warehouse = repository.findById(id);
        if (warehouse.isPresent()) {
            return ResponseEntity.ok(warehouse.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/warehouse/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- Product Endpoints ---

    // DTO for adding a product
    public static class ProductRequest {
        public String warehouseID;
        public ProductData product;
    }

    @PostMapping("/product")
    public ResponseEntity<Warehouse> addProduct(@RequestBody ProductRequest request) {
        if (request.warehouseID == null || request.product == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Warehouse> warehouseOpt = repository.findById(request.warehouseID);
        if (warehouseOpt.isPresent()) {
            Warehouse warehouse = warehouseOpt.get();
            warehouse.addProduct(request.product);
            return ResponseEntity.ok(repository.save(warehouse));
        }
        return ResponseEntity.notFound().build();
    }

    // DTO for returning product with warehouse info
    public static class ProductResponse {
        public String warehouseID;
        public String warehouseName;
        public ProductData product;

        public ProductResponse(String warehouseID, String warehouseName, ProductData product) {
            this.warehouseID = warehouseID;
            this.warehouseName = warehouseName;
            this.product = product;
        }
    }

    @GetMapping("/product")
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> responses = new ArrayList<>();
        List<Warehouse> warehouses = repository.findAll();
        for (Warehouse w : warehouses) {
            if (w.getProductData() != null) {
                for (ProductData p : w.getProductData()) {
                    responses.add(new ProductResponse(w.getWarehouseID(), w.getWarehouseName(), p));
                }
            }
        }
        return responses;
    }

    @GetMapping("/product/{id}")
    public List<ProductResponse> getProductById(@PathVariable String id) {
        List<ProductResponse> responses = new ArrayList<>();
        List<Warehouse> warehouses = repository.findAll();
        for (Warehouse w : warehouses) {
            if (w.getProductData() != null) {
                for (ProductData p : w.getProductData()) {
                    if (id.equals(p.getProductID())) {
                        responses.add(new ProductResponse(w.getWarehouseID(), w.getWarehouseName(), p));
                    }
                }
            }
        }
        return responses;
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id, @RequestParam(required = false) String warehouseID) {
        List<Warehouse> warehouses;
        if (warehouseID != null) {
            warehouses = new ArrayList<>();
            repository.findById(warehouseID).ifPresent(warehouses::add);
        } else {
            warehouses = repository.findAll();
        }

        for (Warehouse w : warehouses) {
            if (w.getProductData() != null) {
                boolean removed = w.getProductData().removeIf(p -> id.equals(p.getProductID()));
                if (removed) {
                    repository.save(w);
                }
            }
        }
        return ResponseEntity.noContent().build();
    }
}
