package com.company.inventario.controller;

import com.company.inventario.model.Product;
import com.company.inventario.response.ProductResponseRest;
import com.company.inventario.services.IProductService;
import com.company.inventario.util.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins={"http://localhost:4200"})
public class ProductRestController {

    private final IProductService productService;

    public ProductRestController(IProductService productService) {
        super();
        this.productService = productService;
    }

    /**
     *
     * @param picture
     * @param name
     * @param price
     * @param account
     * @param categoriaId
     * @return
     * @throws IOException
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> saveProduct(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam ("name") String name,
            @RequestParam("price") BigDecimal price,
           @RequestParam("account") int account,
           @RequestParam("categoriaId") Long categoriaId ) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setAccount(account);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response= productService.save(product,categoriaId);

        return response;
    }

}
