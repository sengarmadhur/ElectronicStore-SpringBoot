package com.ms.electronic.store.ElectronicStore.controllers;

import com.ms.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.ms.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.ms.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ms.electronic.store.ElectronicStore.dtos.ProductDto;
import com.ms.electronic.store.ElectronicStore.services.CategoryService;
import com.ms.electronic.store.ElectronicStore.services.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        CategoryDto categoryDto1 = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(categoryDto1, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Category is deleted successfully !!")
                .status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return new ResponseEntity<>(categoryService.getAll(pageSize, pageNumber, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId) {
        return new ResponseEntity<>(categoryService.get(categoryId), HttpStatus.OK);
    }

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable String categoryId,
                                                                @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.createWithCategory(productDto, categoryId),
                HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(@PathVariable String categoryId, @PathVariable String productId) {
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

}
