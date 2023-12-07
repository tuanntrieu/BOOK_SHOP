package com.example.projectbase.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserResponseDto {
    private String id;

    private String username;

    private String roleName;

    private String email;

    private int customerId;

    private String name;

    private String phonenumber;

    private String address;

    private String avatar;
}
