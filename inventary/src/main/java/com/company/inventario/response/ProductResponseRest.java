package com.company.inventario.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProductResponseRest extends ResponseRest{

     private ProductResponse productResponse = new ProductResponse();
}
