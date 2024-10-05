package com.ms.electronic.store.ElectronicStore.repositories;

import com.ms.electronic.store.ElectronicStore.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
