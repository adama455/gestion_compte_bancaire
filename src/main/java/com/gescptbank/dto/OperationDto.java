package com.gescptbank.dto;

import com.gescptbank.entities.CompteBancaire;
import com.gescptbank.enums.TypeOperation;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {

    private long id;
    private Date dateOperation;
    private double amount;
    private TypeOperation type;
    private String  numCompte;
    private CompteBancaire compte;
    private String  numCompteDestination;
    private String  numCompteSource;
}
