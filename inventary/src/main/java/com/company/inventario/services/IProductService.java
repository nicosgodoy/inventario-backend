package com.company.inventario.services;

import com.company.inventario.model.Product;
import com.company.inventario.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface IProductService {

    public ResponseEntity<ProductResponseRest> save(Product product,Long categoryId);

}
