package com.timife.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shop_item")
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String price;

    private String frontImage;

    private String tag;

    private String sustainability;

    private String careGuide;

    private String shippingAndReturns;

    private String averageRating;

    private Long ratingCount;

    private String details;

    private Collection<Double> sizes;

    private Long genderId;

    private Long categoryid;

//    @OneToMany(mappedBy = "shopItem", cascade = CascadeType.ALL)
//    private Collection<Review> reviews = new ArrayList<>();

    @OneToMany
    private Collection<String> images = new ArrayList<>();

}
