package com.subhash.ims.dto.response;

import com.subhash.ims.entity.enums.ProductStatus;
import com.subhash.ims.entity.enums.UnitType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponseDto {

    private Long id;

    private String sku;

    private String barcode;

    private String name;

    private String description;

    private String brand;

    private BigDecimal purchasePrice;

    private BigDecimal sellingPrice;

    private BigDecimal discount;

    private BigDecimal taxPercentage;

    private UnitType unit;

    private Double weight;

    private String imageUrl;

    private ProductStatus status;

    private Long categoryId;

    private String categoryName;
}
