package com.timife.controllers;

import com.timife.model.dtos.*;
import com.timife.model.entities.Size;
import com.timife.services.CategoryService;
import com.timife.services.GenderService;
import com.timife.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.timife.utils.ValidationUtils.errorEntity;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class CategoriesController {

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final GenderService genderService;

    @Autowired
    private final ProductService productService;


    @PostMapping("/category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto request) {
        try {
            return ResponseEntity.ok(categoryService.createCategory(request));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.CREATED);
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto request) {
        try {
            return ResponseEntity.ok(categoryService.updateCategory(id, request));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.CREATED);
        }
    }

    @PostMapping("/gender")
    public ResponseEntity<?> saveGender(@RequestBody GenderDto request) {
        try {
            return ResponseEntity.ok(genderService.createGender(request));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.CREATED);
        }
    }

    @PutMapping("/gender/{id}")
    public ResponseEntity<?> updateGender(@PathVariable("id") Long id,@RequestBody GenderDto request) {
        try {
            return ResponseEntity.ok(genderService.updateGender(id,request));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.CREATED);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.ok(categoryService.getAllCategories());
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getGenderCategories(
            @PathVariable("id") Long genderId
    ) {
        try {
            return ResponseEntity.ok(categoryService.getAllCategories().stream().filter((category) -> Objects.equals(category.getGenderId(), genderId)));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/genders")
    public ResponseEntity<?> getAllGenders() {
        try {
            return ResponseEntity.ok(genderService.getAllGenders());
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequest request){
        try{
            return ResponseEntity.ok(productService.saveProduct(request));
        }catch (Exception e){
            return errorEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/size")
    public ResponseEntity<?> saveSize(@RequestBody List<Size> sizes){
        try{
            log.error(sizes.toString());
            return ResponseEntity.ok(productService.saveSize(sizes));
        }catch (Exception e){
            return errorEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.ok(productService.getProducts());
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/size")
    public ResponseEntity<?> getAllSizes() {
        try {
            return ResponseEntity.ok(productService.getSizes());
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.NO_CONTENT);
        }
    }

}