package hattabi.youness.web_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hattabi.youness.web_app.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
