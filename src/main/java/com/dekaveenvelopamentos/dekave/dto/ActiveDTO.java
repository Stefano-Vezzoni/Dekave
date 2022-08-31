package com.dekaveenvelopamentos.dekave.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveDTO {

    @NotNull
    private Boolean active;
}
