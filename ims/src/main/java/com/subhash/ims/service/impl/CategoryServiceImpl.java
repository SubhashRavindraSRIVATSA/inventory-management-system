package com.subhash.ims.service.impl;

import com.subhash.ims.dto.request.CategoryRequestDto;
import com.subhash.ims.dto.response.CategoryResponseDto;
import com.subhash.ims.entity.Category;
import com.subhash.ims.exception.BadRequestException;
import com.subhash.ims.exception.ResourceAlreadyExistsException;
import com.subhash.ims.exception.ResourceNotFoundException;
import com.subhash.ims.mapper.CategoryMapper;
import com.subhash.ims.repository.CategoryRepository;
import com.subhash.ims.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto request) {
        log.info("Creating category : {}", request.getName());
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ResourceAlreadyExistsException(
                    "Category already exists : " + request.getName());
        }

        Category category = categoryMapper.toEntity(request);

        if (request.getParentCategoryId() != null) {
            Category parent = categoryRepository.findById(request.getParentCategoryId())
                    .orElseThrow(() ->new ResourceNotFoundException("Parent category not found"));
            category.setParentCategory(parent);
        }

        Category savedCategory = categoryRepository.save(category);
        log.info("Category created successfully : {}", savedCategory.getId());
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto request) {

        log.info("Updating category : {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + id));

        if (!category.getName().equalsIgnoreCase(request.getName())
                && categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ResourceAlreadyExistsException("Category already exists : " + request.getName());
        }

        categoryMapper.updateEntity(request, category);

        if (request.getParentCategoryId() != null) {
            Category parent = categoryRepository.findById(request.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParentCategory(parent);
        } else {
            category.setParentCategory(null);
        }

        Category updatedCategory = categoryRepository.save(category);
        log.info("Category updated successfully : {}", id);
        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {

        log.info("Deleting category : {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + id));

        if (!category.getSubCategories().isEmpty()) {
            throw new BadRequestException("Cannot delete category having sub categories.");
        }

        categoryRepository.delete(category);
        log.info("Category deleted successfully : {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(Long id) {

        log.info("Fetching category : {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories() {

        log.info("Fetching all categories");
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getRootCategories() {

        log.info("Fetching root categories");
        return categoryRepository.findByParentCategoryIsNull()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getSubCategories(Long parentId) {

        log.info("Fetching child categories of : {}", parentId);
        if (!categoryRepository.existsById(parentId)) {
            throw new ResourceNotFoundException("Parent category not found.");
        }

        return categoryRepository.findByParentCategoryId(parentId)
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

}