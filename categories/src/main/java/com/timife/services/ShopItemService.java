package com.timife.services;

import com.timife.model.dtos.ShopItemDto;
import com.timife.model.entities.ShopItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


public interface ShopItemService {

    ShopItem saveAndUpdateShopItem(ShopItemDto shopItemDto);
}
