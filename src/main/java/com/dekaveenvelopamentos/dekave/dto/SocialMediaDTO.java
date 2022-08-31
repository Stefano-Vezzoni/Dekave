package com.dekaveenvelopamentos.dekave.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialMediaDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String icon;

    @NotBlank
    private String url;
}
