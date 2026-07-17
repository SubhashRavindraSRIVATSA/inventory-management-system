package com.subhash.ims.service;

import com.subhash.ims.dto.request.ProductRequestDto;
import com.subhash.ims.dto.response.ProductResponseDto;
import com.subhash.ims.entity.enums.ProductStatus;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto request);

    ProductResponseDto updateProduct(Long id, ProductRequestDto request);

    void deleteProduct(Long id);

    ProductResponseDto getProductById(Long id);

    List<ProductResponseDto> getAllProducts();

    List<ProductResponseDto> getProductsByCategory(Long categoryId);

    List<ProductResponseDto> getProductsByStatus(ProductStatus status);

    List<ProductResponseDto> searchProducts(String keyword);
}
