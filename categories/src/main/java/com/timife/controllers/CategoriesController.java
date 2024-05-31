package com.timife.controllers;

import com.timife.model.dtos.CategoryDto;
import com.timife.model.dtos.GenderDto;
import com.timife.models.requests.UserRequestDto;
import com.timife.repositories.RefreshTokenRepository;
import com.timife.services.AuthenticationService;
import com.timife.services.CategoryService;
import com.timife.services.GenderService;
import com.timife.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.timife.utils.ValidationUtils.errorEntity;
import static com.timife.utils.ValidationUtils.validatePasswordAndEmail;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoryService categoryService;

    private final GenderService genderService;

    @PostMapping("/categories")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto request) {
        try {
            return ResponseEntity.ok(categoryService.createCategory(request));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @RequestBody CategoryDto request) {
        try {
            return ResponseEntity.ok(categoryService.updateCategory(id, request));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/gender")
    public ResponseEntity<?> saveGender(@RequestBody GenderDto request) {
        try {
            return ResponseEntity.ok(genderService.createUpdateGender(request));
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

    @GetMapping("/genders")
    public ResponseEntity<?> getAllGenders() {
        try {
            return ResponseEntity.ok(genderService.getAllGenders());
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}