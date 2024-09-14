package com.ms.electronic.store.ElectronicStore.repositories;

import com.ms.electronic.store.ElectronicStore.entities.Category;
import com.ms.electronic.store.ElectronicStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByTitleContaining(String subTitle, Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findProductsByCategory(Category category, Pageable pageable);
}
