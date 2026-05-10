package com.company.inventario.repository;

import com.company.inventario.model.Category;
import com.company.inventario.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category, Long> {


}