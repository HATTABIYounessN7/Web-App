package hattabi.youness.web_app.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hattabi.youness.web_app.entities.Product;
import hattabi.youness.web_app.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping("/products")
    public String products(@RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice,
            Model model) {

        if (minPrice == null)
            minPrice = 0.0;
        if (maxPrice == null)
            maxPrice = Double.MAX_VALUE;

        Pageable pageable = PageRequest.of(page, size,
                sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

        Page<Product> productPage = productRepository
                .findByNameContainingIgnoreCaseAndPriceBetween(keyword, minPrice, maxPrice, pageable);

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("keyword", keyword);
        model.addAttribute("minPrice", minPrice == 0.0 ? "" : minPrice);
        model.addAttribute("maxPrice", maxPrice == Double.MAX_VALUE ? "" : maxPrice);
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
