package com.ms.electronic.store.ElectronicStore.services;

import com.ms.electronic.store.ElectronicStore.dtos.CategoryDto;
import com.ms.electronic.store.ElectronicStore.dtos.PageableResponse;

public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto, String categoryId);

    void delete(String categoryId);

    PageableResponse<CategoryDto> getAll(int pageSize, int pageNumber, String sortBy, String sortDir);

    CategoryDto get(String categoryId);
    //search

}
