package com.company.inventario.response;

import com.company.inventario.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {

    private List<Category> category ;

}
