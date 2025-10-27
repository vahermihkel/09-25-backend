package ee.mihkel.backend.repository;

import ee.mihkel.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_Name(String name);
}
