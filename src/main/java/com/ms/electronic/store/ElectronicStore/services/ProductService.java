package com.ms.electronic.store.ElectronicStore.services;

import com.ms.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.ms.electronic.store.ElectronicStore.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto, String productId);

    void delete(String productId);

    ProductDto get(String productId);

    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> searchByTitle(String title, int pageNumber, int pageSize, String sortBy, String sortDir);
}
