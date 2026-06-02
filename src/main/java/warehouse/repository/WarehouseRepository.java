package warehouse.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import warehouse.model.Warehouse;
import java.util.Optional;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
    Optional<Warehouse> findById(String warehouseID);
}
