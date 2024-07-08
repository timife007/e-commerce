package com.timife.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressDto {
    private Long id;
    private Long userId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postCode;
    private Boolean isDefault;
    private Boolean isBilling;
}
