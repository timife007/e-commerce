package com.timife.services.impl;

import com.timife.model.dtos.ProductRequest;
import com.timife.model.entities.*;
import com.timife.model.responses.ProductResponse;
import com.timife.repositories.*;
import com.timife.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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


    @Transactional
    @Override
    public Product saveProduct(ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

        Product newProduct = createProduct(productRequest, category);
        Product savedProduct = productRepository.save(newProduct);

        //Handle Image relationships
        List<Image> images = productRequest.getImages().stream().peek((image) -> image.setProduct(savedProduct)
        ).toList();
        //Save all images.
//        var savedImages = imageRepository.saveAll(images);
        savedProduct.getImages().addAll(images);

        Colour savedColour = colourRepository.save(productRequest.getColour());

        savedProduct.setColour(savedColour);

        //Handle product size relationships, child and parent.
        List<ProductSize> productSizes = productRequest.getProductSizeList().stream().map((request) -> {
            Size size = sizeRepository.findById(request.getSizeId()).orElseThrow(() -> new RuntimeException("Size not found"));
            ProductSize productSize = new ProductSize();
            productSize.setSize(size);
            productSize.setProduct(savedProduct);
            productSize.setQtyInStock(request.getQtyInStock());
            return productSize;
        }).toList();

        //Save all product sizes.
//        List<ProductSize> savedProductSizes = productSizeRepository.saveAll(productSizes);
        savedProduct.getProductSizes().addAll(productSizes);
//        productRepository.save(savedProduct);
        return productRepository.save(savedProduct);
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

    private static Product createProduct(ProductRequest productRequest, Category category) {
        Product newProduct = new Product();
        newProduct.setName(productRequest.getProductName());
        newProduct.setSalePrice(productRequest.getSalePrice());
        newProduct.setProductCode(productRequest.getProductCode());
        newProduct.setBrand(productRequest.getBrand());
        newProduct.setOriginalPrice(productRequest.getOriginalPrice());
        newProduct.setProductDescription(productRequest.getProductDescription());
        newProduct.setCategory(category);
        newProduct.setColour(productRequest.getColour());
        return newProduct;
    }
}
