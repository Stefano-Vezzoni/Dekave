package com.dekaveenvelopamentos.dekave.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorMessageEnum {

    ID_NOT_FOUND("O id informado não foi encontrado, verifique se está correto."),
    NOT_BLANK("Verifique se todos os campos estão preenchidos corretamente."),
    FILE_NOT_FOUND("O arquivo não foi encontrado.");

    private String message;
}
