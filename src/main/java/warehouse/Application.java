package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import warehouse.model.ProductData;
import warehouse.model.Warehouse;
import warehouse.repository.WarehouseRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private WarehouseRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        Warehouse w1 = new Warehouse("1", "Linz Bahnhof", 
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 
                4010, "Linz", "Austria");

        w1.addProduct(new ProductData("00-443175", "Bio Orangensaft Sonne", "Getraenk", 2500));
        w1.addProduct(new ProductData("00-871895", "Bio Apfelsaft Gold", "Getraenk", 3420));
        w1.addProduct(new ProductData("00-111111", "Wasser Still", "Getraenk", 10000));
        w1.addProduct(new ProductData("00-222222", "Wasser Prickelnd", "Getraenk", 8500));

        w1.addProduct(new ProductData("01-926885", "Ariel Waschmittel Color", "Waschmittel", 478));
        w1.addProduct(new ProductData("01-111111", "Persil Universal", "Waschmittel", 300));
        w1.addProduct(new ProductData("01-222222", "Weichspueler Sommerwind", "Waschmittel", 150));

        w1.addProduct(new ProductData("02-234811", "Mampfi Katzenfutter Rind", "Tierfutter", 1324));
        w1.addProduct(new ProductData("02-111111", "Hundefutter Premium", "Tierfutter", 800));
        w1.addProduct(new ProductData("02-222222", "Vogelfutter Mix", "Tierfutter", 2000));

        repository.save(w1);

        System.out.println("Initialization complete:");
        System.out.println("Added Warehouse '1' with 10 products across 3 categories.");
    }
}
