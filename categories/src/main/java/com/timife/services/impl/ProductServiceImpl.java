package com.timife.services.impl;

import com.timife.model.dtos.ProductRequest;
import com.timife.model.dtos.SelectOrderDto;
import com.timife.model.entities.*;
import com.timife.model.responses.ProductResponse;
import com.timife.model.responses.ProductSizeResponse;
import com.timife.repositories.*;
import com.timife.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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


    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        Product newProduct = createProduct(productRequest, category);

        //Handle Image relationships
        Set<Image> images = productRequest.getImages().stream().map((request) ->
                Image.builder().imageUrl(request.getImageUrl()).type(request.getType()).build()
        ).collect(Collectors.toSet());

        //Save all images.
        newProduct.setImages(images);

        Brand savedBrand = brandRepository.save(productRequest.getBrand());
        newProduct.setBrand(savedBrand);

        Colour savedColour = colourRepository.save(Colour.builder().colour(productRequest.getColour()).build());
        newProduct.setColour(savedColour);


        //Handle product size relationships, child and parent.
        List<ProductSize> productSizes = productRequest.getProductSizeList().stream().map((request) -> {
            Size size = sizeRepository.findById(request.getSizeId()).orElseThrow(() -> new RuntimeException("Size not found"));
            ProductSize productSize = new ProductSize();
            productSize.setSize(size);
            productSize.setQtyInStock(request.getQtyInStock());
            productSize.setProduct(newProduct);
            return productSize;
        }).toList();

        //Save all product sizes
        newProduct.getProductSizes().addAll(productSizes);
        Product savedProduct = productRepository.save(newProduct);
        ProductResponse response = ProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .salePrice(savedProduct.getSalePrice())
                .productCode(savedProduct.getProductCode())
                .productDetails(savedProduct.getProductDescription())
                .categoryId(category.getId())
                .sizes(savedProduct.getProductSizes().stream().map((productSize) -> Size.builder().size(productSize.getSize().getSize()).id(productSize.getSize().getId()).build()
                ).toList())
                .colour(savedProduct.getColour().getColour())
                .imageList(savedProduct.getImages().stream().toList())
                .brand(savedProduct.getBrand())
                .productSizes(savedProduct.getProductSizes().stream().toList())
                .originalPrice(savedProduct.getOriginalPrice())
                .lookAfterMe(savedProduct.getLookAfterMe())
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

    @Override
    public List<ProductSizeResponse> getSpecificProductSizeQty(Long id) {
        return productSizeRepository.findAll().stream().filter((item) -> Objects.equals(item.getProduct().getId(), id)).map((productSize) ->
                ProductSizeResponse.builder().sizeId(productSize.getId()).qtyInStock(productSize.getQtyInStock()).id(productSize.getId()).price(productSize.getProduct().getSalePrice()).build()
        ).toList();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductSizeResponse selectOrderRequest(SelectOrderDto selectOrderDto) {
        ProductSize productSize = productSizeRepository.findByProductIdAndSizeId(selectOrderDto.getProductId(), selectOrderDto.getSizeId()).orElseThrow(
                () -> new RuntimeException("Product Item with size id " + selectOrderDto.getSizeId() + "and product id " + selectOrderDto.getProductId() + "not found")
        );
        return ProductSizeResponse.builder()
                .sizeId(productSize.getId())
                .qtyInStock(productSize.getQtyInStock())
                .id(productSize.getId())
                .price(productSize.getProduct()
                        .getSalePrice()).build();
    }

    @Override
    public List<Image> getSpecificImages(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found")).getImages().stream().toList();
    }

    @Override
    public ProductResponse getProduct(Long id) {
        Product savedProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));

        return ProductResponse.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .salePrice(savedProduct.getSalePrice())
                .productCode(savedProduct.getProductCode())
                .productDetails(savedProduct.getProductDescription())
                .categoryId(savedProduct.getCategory().getId())
                .sizes(savedProduct.getProductSizes().stream().map((productSize) -> Size.builder().size(productSize.getSize().getSize()).id(productSize.getSize().getId()).build()
                ).toList())
                .colour(savedProduct.getColour().getColour())
                .imageList(savedProduct.getImages().stream().toList())
                .brand(savedProduct.getBrand())
                .productSizes(savedProduct.getProductSizes().stream().toList())
                .originalPrice(savedProduct.getOriginalPrice())
                .lookAfterMe(savedProduct.getLookAfterMe())
                .build();
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
