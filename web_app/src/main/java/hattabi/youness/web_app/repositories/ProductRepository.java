package hattabi.youness.web_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hattabi.youness.web_app.entities.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
}
