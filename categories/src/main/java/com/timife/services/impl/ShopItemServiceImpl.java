package com.timife.services.impl;

import com.timife.model.dtos.ShopItemDto;
import com.timife.model.entities.ShopItem;
import com.timife.repositories.CategoryRepository;
import com.timife.repositories.GenderRepository;
import com.timife.repositories.ShopItemRepository;
import com.timife.repositories.SubCategoryRepository;
import com.timife.services.ShopItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopItemServiceImpl implements ShopItemService {

    @Autowired
    private final ShopItemRepository shopItemRepository;
    @Autowired
    private final GenderRepository genderRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public ShopItem saveAndUpdateShopItem(ShopItemDto shopItemDto) {
        ShopItem currentShopItem = shopItemRepository.findByIdAndSubCategory(shopItemDto.getName(), shopItemDto.getCategoryId(), shopItemDto.getSubCategoryId(), shopItemDto.getGenderId());
        if (currentShopItem == null) {
            ShopItem newItem = ShopItem
                    .builder()
                    .name(shopItemDto.getName())
                    .price(shopItemDto.getPrice())
                    .frontImage(shopItemDto.getFrontImage())
                    .images(shopItemDto.getImages())
                    .careGuide(shopItemDto.getCareGuide())
                    .genderCategory(genderRepository.findById(shopItemDto.getGenderId()).orElseThrow())
                    .category(categoryRepository.findById(shopItemDto.getCategoryId()).orElseThrow())
                    .subCategory(subCategoryRepository.findById(shopItemDto.getSubCategoryId()).orElseThrow())
                    .tag(shopItemDto.getTag())
                    .shippingAndReturns(shopItemDto.getShippingAndReturns())
                    .sustainability(shopItemDto.getSustainability())
                    .details(shopItemDto.getDetails()).build();
            return shopItemRepository.save(newItem);
        }
        currentShopItem.setDetails(shopItemDto.getDetails());
        currentShopItem.setCareGuide(shopItemDto.getCareGuide());
        currentShopItem.setImages(shopItemDto.getImages());
        currentShopItem.setName(shopItemDto.getName());
        currentShopItem.setFrontImage(shopItemDto.getFrontImage());
        return shopItemRepository.save(currentShopItem);
    }
}
