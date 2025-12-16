package com.example.Ecommerce.Service;

import com.example.Ecommerce.Payload.CategoryDTO;
import com.example.Ecommerce.Payload.CategoryResponse;
import com.example.Ecommerce.exceptions.APIException;
import com.example.Ecommerce.exceptions.ResourceNotFoundException;
import com.example.Ecommerce.model.Category;
import com.example.Ecommerce.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService{


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();

        if(categories.isEmpty()){
            throw new APIException("No Categories exist");
        }

        else{
          List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category,CategoryDTO.class))
                                            .toList();

          CategoryResponse categoryResponse = new CategoryResponse();

          categoryResponse.setContent(categoryDTOS);
          categoryResponse.setPageNumber(categoryPage.getNumber());
          categoryResponse.setPageSize(categoryPage.getSize());
          categoryResponse.setTotalElements(categoryPage.getTotalElements());
          categoryResponse.setTotalPages(categoryPage.getTotalPages());
          categoryResponse.setLastPage(categoryPage.isLast());

          return categoryResponse;
        }

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

        Category category = modelMapper.map(categoryDTO,Category.class);

        //checking if this category exist or not
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        //Till here

        //check later if this line is needed or not
        category.setCategoryId(categoryId);
        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory,CategoryDTO.class);

    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        //checking if this category exist or not
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        CategoryDTO deletedCategoryDTO = modelMapper.map(category,CategoryDTO.class);
        categoryRepository.delete(category);

        return deletedCategoryDTO;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO,Category.class);

        Category existCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (existCategory != null){
            throw new APIException("Category with the name " + categoryDTO.getCategoryName() + " already exists.");
        }


        Category savedcategory = categoryRepository.save(category);
        return modelMapper.map(savedcategory,CategoryDTO.class);
    }


}
