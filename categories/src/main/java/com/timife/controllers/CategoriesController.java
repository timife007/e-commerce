package com.timife.controllers;

import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;
import com.timife.services.CategoryService;
import com.timife.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.timife.utils.ValidationUtils.errorEntity;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class CategoriesController {

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final GenderService genderService;

    @PostMapping("/category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto request) {
        try {
            return ResponseEntity.ok(categoryService.createCategory(request));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto request) {
        try {
            return ResponseEntity.ok(categoryService.updateCategory(id, request));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/gender")
    public ResponseEntity<?> saveGender(@RequestBody GenderDto request) {
        try {
            return ResponseEntity.ok(genderService.createGender(request));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/gender/{id}")
    public ResponseEntity<?> updateGender(@PathVariable Long id,@RequestBody GenderDto request) {
        try {
            return ResponseEntity.ok(genderService.updateGender(id,request));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.ok(categoryService.getAllCategories());
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getGenderCategories(
            @PathVariable("id") Long genderId
    ) {
        try {
            return ResponseEntity.ok(categoryService.getAllCategories().stream().filter((category) -> Objects.equals(category.getGenderId(), genderId)));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/genders")
    public ResponseEntity<?> getAllGenders() {
        try {
            return ResponseEntity.ok(genderService.getAllGenders());
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}