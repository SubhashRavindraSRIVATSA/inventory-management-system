package com.subhash.ims.service.impl;

import com.subhash.ims.dto.request.ProductRequestDto;
import com.subhash.ims.dto.response.ProductResponseDto;
import com.subhash.ims.entity.Category;
import com.subhash.ims.entity.Product;
import com.subhash.ims.entity.enums.ProductStatus;
import com.subhash.ims.exception.ResourceAlreadyExistsException;
import com.subhash.ims.exception.ResourceNotFoundException;
import com.subhash.ims.mapper.ProductMapper;
import com.subhash.ims.repository.CategoryRepository;
import com.subhash.ims.repository.ProductRepository;
import com.subhash.ims.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto request) {
        log.info("Creating product : {}", request.getName());
        if (productRepository.existsBySku(request.getSku())) {
            throw new ResourceAlreadyExistsException("SKU already exists : " + request.getSku());
        }

        if (request.getBarcode() != null &&!request.getBarcode().isBlank() && productRepository.existsByBarcode(request.getBarcode())) {
            throw new ResourceAlreadyExistsException("Barcode already exists : " + request.getBarcode());
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + request.getCategoryId()));

        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        product.setStatus(ProductStatus.ACTIVE);
        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully : {}", savedProduct.getId());
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto request) {
        log.info("Updating product : {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));

        if (!product.getSku().equals(request.getSku()) && productRepository.existsBySku(request.getSku())) {
            throw new ResourceAlreadyExistsException("SKU already exists : " + request.getSku());
        }

        if (request.getBarcode() != null && !request.getBarcode().isBlank()) {
            if (!request.getBarcode().equals(product.getBarcode()) && productRepository.existsByBarcode(request.getBarcode())) {
                throw new ResourceAlreadyExistsException("Barcode already exists : " + request.getBarcode());
            }
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id : "+ request.getCategoryId()));

        productMapper.updateEntity(request, product);
        product.setCategory(category);
        Product updatedProduct = productRepository.save(product);
        log.info("Product updated successfully : {}", id);
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product : {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
        productRepository.delete(product);
        log.info("Product deleted successfully : {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        log.info("Fetching product : {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + id));
        return productMapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
        log.info("Fetching products for category : {}", categoryId);
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByStatus(ProductStatus status) {
        log.info("Fetching products with status : {}", status);
        return productRepository.findByStatus(status)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> searchProducts(String keyword) {
        log.info("Searching products : {}", keyword);
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
