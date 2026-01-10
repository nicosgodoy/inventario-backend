package com.company.inventario.dao;

import com.company.inventario.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryDao extends CrudRepository<Category, Long> {



}
