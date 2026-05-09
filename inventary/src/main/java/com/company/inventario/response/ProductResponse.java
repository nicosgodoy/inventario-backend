package com.company.inventario.response;

import com.company.inventario.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    List<Product> products;
}
