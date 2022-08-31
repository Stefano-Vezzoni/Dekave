package com.dekaveenvelopamentos.dekave.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotBlank
    private String copyright;

    @NotBlank
    private String contactMessage;
}
