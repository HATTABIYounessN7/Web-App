package hattabi.youness.web_app.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import hattabi.youness.web_app.entities.Product;
import hattabi.youness.web_app.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping("/products")
    public String products(@RequestParam(required = false) String keyword, Model model) {
        List<Product> productsList;

        if (keyword != null && !keyword.isEmpty()) {
            productsList = productRepository.findByNameContainingIgnoreCase(keyword);
        } else {
            productsList = productRepository.findAll();
        }

        model.addAttribute("products", productsList);
        model.addAttribute("keyword", keyword);
        return "products";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addProduct";
        }

        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/editProduct")
    public String editProduct(@RequestParam Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        model.addAttribute("product", product);
        return "addProduct";
    }

}
