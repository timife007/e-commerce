package com.timife.model.entities;

import com.timife.models.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;

    private Instant date;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private ShopItem shopItem;
}
