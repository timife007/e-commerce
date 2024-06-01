package com.timife.models.requests;

import com.timife.models.Interest;
import com.timife.models.Role;
import com.timife.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User toUser() {
        return new User(
                null,
                this.firstName,
                this.lastName,
                this.email,
                Role.USER,
                "",
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
                this.password,
                Interest.MENSWEAR
        );
    }

}

