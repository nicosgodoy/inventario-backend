package com.company.inventario.controller;

import com.company.inventario.model.Product;
import com.company.inventario.response.CategoryResponseRest;
import com.company.inventario.response.ProductResponseRest;
import com.company.inventario.services.IProductService;
import com.company.inventario.util.CategoryExcelExport;
import com.company.inventario.util.ProductExcelExport;
import com.company.inventario.util.Util;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
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
     * save product
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
           @RequestParam("categoryId") Long categoriaId ) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setAccount(account);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response= productService.save(product,categoriaId);

        return response;
    }

    /**
     * search product by id
     * @param id
     * @return
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> searchById(@PathVariable("id") Long id) {
        ResponseEntity<ProductResponseRest> response = productService.searchById(id);
        return response;
    }

    /**
     * search product by name
     * @param name
     * @return
     */
    @GetMapping("/products/filter/{name}")
    public ResponseEntity<ProductResponseRest> searchByName(@PathVariable("name") String name) {
        ResponseEntity<ProductResponseRest> response = productService.searchByName(name);
        return response;
    }

    /**
     * delete product by id
     * @param id
     * @return
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> deleteById(@PathVariable("id") Long id) {
        ResponseEntity<ProductResponseRest> response = productService.deleteById(id);
        return response;
    }

    /**
     * search all products
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<ProductResponseRest> searchAll() {
        ResponseEntity<ProductResponseRest> response = productService.searchAll();
        return response;
    }

    /**
     * update product by id
     * @param picture
     * @param name
     * @param price
     * @param account
     * @param categoryId
     * @param id
     * @return
     * @throws IOException
     */
    @PutMapping ("/products/{id}")
    public ResponseEntity<ProductResponseRest> updateProduct(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam ("name") String name,
            @RequestParam("price") BigDecimal price,
            @RequestParam("account") int account,
            @RequestParam("categoryId") Long categoryId ,
            @PathVariable("id") Long id)  throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setAccount(account);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        return productService.update(product, categoryId, id);
    }

    /**
     * export products to excel
     * @param response
     * @throws IOException
     */
    @GetMapping("/products/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=result_product.xlsx";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<ProductResponseRest> product = productService.searchAll();

        if (product.getBody() == null) {
            return;
        }
        ProductExcelExport excelExporter = new ProductExcelExport(product.getBody().getProductResponse().getProducts());
        excelExporter.export(response);

        System.out.println(product.getBody());
    }

}
