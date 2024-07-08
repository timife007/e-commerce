package com.timife.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private  Integer userId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private Boolean isDefault;
    private Boolean isBilling;
}