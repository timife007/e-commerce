package com.timife.services;

import com.timife.model.dtos.ProductRequest;
import com.timife.model.dtos.SelectOrderDto;
import com.timife.model.entities.Image;
import com.timife.model.entities.Product;
import com.timife.model.entities.ProductSize;
import com.timife.model.entities.Size;
import com.timife.model.responses.ProductResponse;
import com.timife.model.responses.ProductSizeResponse;

import java.util.List;

public interface ProductService {

    public ProductResponse saveProduct(ProductRequest productRequest);

    public List<Product> getProducts();

    public List<Size> saveSize(List<Size> size);

    public List<Size> getSizes();

    public List<ProductSize> getProductSizes();

    public List<Image> getImages();

    public List<ProductSizeResponse> getSpecificProductSizeQty(Long id);

    public List<Image> getSpecificImages(Long productId);

    public ProductResponse getProduct(Long id);

    public void deleteProduct(Long id);

    public ProductSizeResponse selectOrderRequest(SelectOrderDto selectOrderDto);
}
