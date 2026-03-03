package hattabi.youness.web_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hattabi.youness.web_app.entities.Product;
import hattabi.youness.web_app.repositories.ProductRepository;

@SpringBootApplication
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProductRepository productRepository) {
		return args -> {
			productRepository.save(Product.builder()
					.name("Computer")
					.price(8000)
					.quantity(5)
					.build());

			productRepository.save(Product.builder()
					.name("Printer")
					.price(1200)
					.quantity(10)
					.build());

			productRepository.findAll().forEach(p -> {
				System.out.println(p.getName());
			});
		};
	}

}
