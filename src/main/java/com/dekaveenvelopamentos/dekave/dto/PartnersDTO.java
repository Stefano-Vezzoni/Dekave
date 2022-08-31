package com.dekaveenvelopamentos.dekave.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnersDTO {

    @NotBlank
    private String name;
}
