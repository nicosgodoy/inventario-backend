package com.company.inventario.repository;

import com.company.inventario.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product,Long> {
}
