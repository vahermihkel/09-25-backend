package ee.mihkel.backend.controller;

import ee.mihkel.backend.entity.Product;
import ee.mihkel.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.Double.NaN;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ProductController {

    // Dependency Injection
    @Autowired
    ProductRepository productRepository;

    // GET localhost:8080/products  --->   products
    @GetMapping("products")
    public List<Product> products(){
        return productRepository.findAll(); // SELECT * FROM products
    }

    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product){
        if (product.getId() != null){
            throw new RuntimeException("Cannot add product with ID");
        }
        if (product.getName() == null || product.getName().isEmpty()){
            throw new RuntimeException("Cannot add product without name");
        }
        if (product.getPrice() <= 0 || Double.isNaN(product.getPrice())){
            throw new RuntimeException("Cannot add product with negative price");
        }
        productRepository.save(product); // INSERT INTO products
        return productRepository.findAll(); // SELECT * FROM products
    }

    @GetMapping("products/{id}")
    public Product getProduct(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow(); // viskab errori kui ei leia
    }

    @PutMapping("products")
    public List<Product> editProduct(@RequestBody Product product){
        if (product.getId() == null || product.getId() <= 0){
            throw new RuntimeException("Cannot edit product without ID");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }

    @DeleteMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    // localhost:8080/category-products?categoryId=1
    @GetMapping("category-products")
    public List<Product> getProductsByCategory(@RequestParam String categoryName){
        return productRepository.findByCategory_Name(categoryName);
    }

    @PostMapping("add-all-products")
    public String addAllProducts(@RequestBody List<Product> products){
        productRepository.saveAll(products);
        return "All products added";
    }
}
