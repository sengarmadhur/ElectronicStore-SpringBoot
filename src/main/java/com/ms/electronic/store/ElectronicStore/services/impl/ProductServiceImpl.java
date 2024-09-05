package com.ms.electronic.store.ElectronicStore.services.impl;

import com.ms.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ms.electronic.store.ElectronicStore.dtos.ProductDto;
import com.ms.electronic.store.ElectronicStore.entities.Product;
import com.ms.electronic.store.ElectronicStore.exceptions.ResourceNotFoundException;
import com.ms.electronic.store.ElectronicStore.helper.Helper;
import com.ms.electronic.store.ElectronicStore.repositories.ProductRepository;
import com.ms.electronic.store.ElectronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDto create(ProductDto productDto) {
        String id = UUID.randomUUID().toString();
        productDto.setProductId(id);
        Product product = mapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setDescription(productDto.getDescription());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setAddedDate(productDto.getAddedDate());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setTitle(productDto.getTitle());
        product.setProductImage(productDto.getProductImage());
        Product updatedProduct = productRepository.save(product);
        return  mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String title, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByTitleContaining(title, pageable);
        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }
}
