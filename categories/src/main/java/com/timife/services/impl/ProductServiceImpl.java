package com.timife.services.impl;

import com.timife.model.dtos.ProductRequest;
import com.timife.model.entities.*;
import com.timife.model.responses.ProductResponse;
import com.timife.repositories.*;
import com.timife.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private productRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ColourRepository colourRepository;

    @Autowired
    private BrandRepository brandRepository;


    @Transactional
    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        Product newProduct = createProduct(productRequest, category);
        Product savedProduct = productRepository.save(newProduct);
        //Handle Image relationships
        List<Image> images = productRequest.getImages().stream().map((request) ->
                Image.builder().imageUrl(request.getImageUrl()).type(request.getType()).build()
        ).toList();
        //Save all images.
        savedProduct.getImages().addAll(images);

        Brand savedBrand = brandRepository.save(productRequest.getBrand());
        savedProduct.setBrand(savedBrand);

        Colour savedColour = colourRepository.save(Colour.builder().colour(productRequest.getColour()).build());
//
        savedProduct.setColour(savedColour);

        //Handle product size relationships, child and parent.
        List<ProductSize> productSizes = productRequest.getProductSizeList().stream().map((request) -> {
            Size size = sizeRepository.findById(request.getSizeId()).orElseThrow(() -> new RuntimeException("Size not found"));
            ProductSize productSize = new ProductSize();
            productSize.setSize(size);
            productSize.setQtyInStock(request.getQtyInStock());
            return productSize;
        }).toList();

//        log.error(productSizes.toString());

        //Save all product sizes.
        savedProduct.getProductSizes().addAll(productSizes);
        productRepository.save(savedProduct);
        ProductResponse response = ProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .salePrice(savedProduct.getSalePrice())
                .productCode(savedProduct.getProductCode())
                .productDetails(savedProduct.getProductDescription())
                .categoryId(category.getId())
                .colour(savedProduct.getColour().getColour())
                .imageList(savedProduct.getImages().stream().toList())
                .brand(savedProduct.getBrand())
                .build();
        return response;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Size> saveSize(List<Size> sizes) {
        return sizeRepository.saveAll(sizes);
    }

    @Override
    public List<Size> getSizes() {
        return sizeRepository.findAll();
    }

    @Override
    public List<ProductSize> getProductSizes() {
        return productSizeRepository.findAll();
    }

    @Override
    public List<Image> getImages() {
        return imageRepository.findAll();
    }

    private static Product createProduct(ProductRequest productRequest, Category category) {
        Product newProduct = new Product();
        newProduct.setName(productRequest.getProductName());
        newProduct.setSalePrice(productRequest.getSalePrice());
        newProduct.setProductCode(productRequest.getProductCode());
        newProduct.setOriginalPrice(productRequest.getOriginalPrice());
        newProduct.setProductDescription(productRequest.getProductDescription());
        newProduct.setCategory(category);
        return newProduct;
    }
}
