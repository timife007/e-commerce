package com.timife.services.impl;

import com.timife.model.entities.ProductItem;
import com.timife.repositories.ProductItemRepository;
import com.timife.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductItemRepository productItemRepository;


    @Override
    public ProductItem save(ProductItem productItem) {

        return null;
    }
}
