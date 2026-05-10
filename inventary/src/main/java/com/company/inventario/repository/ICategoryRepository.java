package com.company.inventario.repository;

import com.company.inventario.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {



}
