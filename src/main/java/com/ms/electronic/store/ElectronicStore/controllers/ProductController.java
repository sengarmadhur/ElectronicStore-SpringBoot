package com.ms.electronic.store.ElectronicStore.controllers;

import com.ms.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.ms.electronic.store.ElectronicStore.dtos.ImageResponse;
import com.ms.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ms.electronic.store.ElectronicStore.dtos.ProductDto;
import com.ms.electronic.store.ElectronicStore.services.FileService;
import com.ms.electronic.store.ElectronicStore.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    public FileService fileService;

    @Value("${product.image.path}")
    private String imageUploadPath;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto productDto1 = productService.create(productDto);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        ProductDto productDto1 = productService.update(productDto, productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponseMessage> deleteProduct(String productId) {
        productService.delete(productId);
        return  new ResponseEntity<>(new ApiResponseMessage("User deleted successfully", true, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/productId")
    public ResponseEntity<ProductDto> get(@PathVariable String productId) {
        ProductDto productDto = productService.get(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                               @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                               @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> search(@PathVariable String query, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                   @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                   @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadImage(@PathVariable String productId,
                                                     @RequestParam("productImage") MultipartFile image) throws IOException {
        String imageName = fileService.uploadImage(image, imageUploadPath);

        ProductDto productDto = productService.get(productId);
        productDto.setProductImage(imageName);
        ProductDto productDto1 = productService.update(productDto, productId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName)
                .message("Image uploaded successfully")
                .status(HttpStatus.CREATED)
                .success(true).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("image/{productId}")
    public void  serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.get(productId);
        InputStream inputStream = fileService.getResource(imageUploadPath, productDto.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }
}
