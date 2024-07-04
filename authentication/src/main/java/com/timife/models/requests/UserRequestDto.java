package com.timife.models.requests;

import com.timife.models.Interest;
import com.timife.models.Role;
import com.timife.models.entities.DeliveryAddress;
import com.timife.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<DeliveryAddress> deliveryAddresses = null;

    public User toUser() {
        return new User(
                null,
                this.firstName,
                this.lastName,
                this.email,
                Role.USER,
                "",
                this.deliveryAddresses,
                this.password,
                Interest.MENSWEAR
        );
    }

    public User toAdminUser() {
        return new User(
                null,
                this.firstName,
                this.lastName,
                this.email,
                Role.ADMIN,
                "",
                this.deliveryAddresses,
                this.password,
                Interest.MENSWEAR
        );
    }

    public User toVendorUser() {
        return new User(
                null,
                this.firstName,
                this.lastName,
                this.email,
                Role.VENDOR,
                "",
                this.deliveryAddresses,
                this.password,
                Interest.MENSWEAR
        );
    }

}

