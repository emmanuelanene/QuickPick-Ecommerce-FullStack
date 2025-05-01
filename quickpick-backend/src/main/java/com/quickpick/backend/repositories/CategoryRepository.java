package com.quickpick.backend.repositories;

import com.quickpick.backend.entities.Address;
import com.quickpick.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
