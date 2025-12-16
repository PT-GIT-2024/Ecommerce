package com.example.Ecommerce.Service;

import com.example.Ecommerce.Payload.CategoryDTO;
import com.example.Ecommerce.Payload.CategoryResponse;


public interface CategoryService {

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
}
