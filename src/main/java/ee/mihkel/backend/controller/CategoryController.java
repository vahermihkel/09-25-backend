package ee.mihkel.backend.controller;

import ee.mihkel.backend.entity.Category;
import ee.mihkel.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    // GET localhost:8080/categories
    @GetMapping("categories")
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    @PostMapping("categories")
    public List<Category> addCategory(@RequestBody Category category){
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }

    @DeleteMapping("categories/{name}")
    public List<Category> deleteCategory(@PathVariable String name){
        categoryRepository.deleteById(name);
        return categoryRepository.findAll();
    }
}
