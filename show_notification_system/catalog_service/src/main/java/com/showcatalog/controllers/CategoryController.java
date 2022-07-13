package com.showcatalog.controllers;

import com.showcatalog.entities.Category;
import com.showcatalog.exceptions.CategoryException;
import com.showcatalog.repositories.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> apiGetAllCategories() throws CategoryException {
        log.trace("apiGetAllCategories");
        List<Category> categories;
        try {
            categories = categoryRepository.findAll();
        } catch (Exception e) {
            throw new CategoryException(e.getMessage());
        }
        return categories;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void apiCreateCategory(@RequestBody Category category) throws CategoryException {
        log.trace("apiCreateCategory");
        log.debug("category " + category);
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new CategoryException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void apiDeleteCategory(@PathVariable Long id) throws CategoryException {
        log.trace("apiDeleteCategory");
        log.debug("id " + id);
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new CategoryException(e.getMessage());
        }
    }

    @ExceptionHandler(value = CategoryException.class)
    public ResponseEntity<Object> handleRest(Exception e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}
