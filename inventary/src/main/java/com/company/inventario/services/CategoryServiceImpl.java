package com.company.inventario.services;

import com.company.inventario.repository.ICategoryRepository;
import com.company.inventario.model.Category;
import com.company.inventario.response.CategoryResponseRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {

        CategoryResponseRest response = new CategoryResponseRest();

        try {
            List<Category> category = (List<Category>) categoryRepository.findAll();
            response.getCategoryResponse().setCategory(category);
            response.setMetadata("Respuesta ok", "00","Respuesta exitosa");


        } catch (Exception e){
            response.setMetadata("Respuesta no Ok","-1", "Error al consultar");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {

        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
           Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                list.add(category.get());
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta Ok", "00", "Categoria encontrada");
            }else {
                response.setMetadata("Respuesta no Ok", "-1", "Categoria no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e){
            response.setMetadata("Respuesta no Ok","-1", "Error al consultar por id");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> save(Category category) {

        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
            Category categorySaved = categoryRepository.save(category);
            if(categorySaved != null) {
                list.add(categorySaved);
                response.getCategoryResponse().setCategory(list);
                response.setMetadata("Respuesta Ok", "00", "Categoria guardada");
            } else {
                response.setMetadata("Respuesta no Ok", "-1", "Categoria no guardada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e){
            response.setMetadata("Respuesta no Ok","-1", "Error al grabar categoria");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> update(Long id, Category category) {
        CategoryResponseRest response = new CategoryResponseRest();
        List<Category> list = new ArrayList<>();
        try {
            Optional<Category> categorySearch = categoryRepository.findById(id);
            if(categorySearch.isPresent()) {
                categorySearch.get().setName(category.getName());
                categorySearch.get().setDescription(category.getDescription());

                Category categoryToUpdate = categoryRepository.save(categorySearch.get());
                if(categoryToUpdate != null){
                    list.add(categoryToUpdate);
                    response.getCategoryResponse().setCategory(list);
                    response.setMetadata("Respuesta ok", "00", "Categoria actualizada");
                }else{
                    response.setMetadata("Respuesta no Ok", "-1", "Categoria no actualizada");
                    return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                response.setMetadata("Respuesta no Ok", "-1", "Categoria no encontrada");
                return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e){
            response.setMetadata("Respuesta no Ok","-1", "Error al actualizar la categoria");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> deleteById(Long id) {
        CategoryResponseRest response = new CategoryResponseRest();

        try {
           categoryRepository.deleteById(id);
           response.setMetadata("Respuesta Ok", "00", "Categoria eliminada");
        } catch (Exception e){
            response.setMetadata("Respuesta no Ok","-1", "Error al eliminar");
            e.getStackTrace();
            return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
    }

}
