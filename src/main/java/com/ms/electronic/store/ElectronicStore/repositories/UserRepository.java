package com.ms.electronic.store.ElectronicStore.repositories;

import com.ms.electronic.store.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
