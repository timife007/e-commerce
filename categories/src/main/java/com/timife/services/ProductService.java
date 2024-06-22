package com.timife.services;

import com.timife.model.dtos.ProductRequest;
import com.timife.model.entities.Product;
import com.timife.model.entities.Size;
import com.timife.model.responses.ProductResponse;

import java.util.List;

public interface ProductService {

    public Product saveProduct(ProductRequest productRequest);

    public List<Product> getProducts();

    public List<Size> saveSize(List<Size> size);

    public List<Size> getSizes();
}
