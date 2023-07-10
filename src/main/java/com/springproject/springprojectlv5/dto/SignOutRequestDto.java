package com.springproject.springprojectlv5.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignOutRequestDto {
    private String username;
    private String password;
}
