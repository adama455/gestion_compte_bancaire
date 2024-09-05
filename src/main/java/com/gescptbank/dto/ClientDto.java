package com.gescptbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private String firstName;
    private  String lastName;
    private String email;
    private String telephone;
    private String birthday;
    private String address;
}
