package com.dekaveenvelopamentos.dekave.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorMessageEnum {

    REORDER_ACTION("Essa ação é inválida."),
    REORDER_POSITION("A posição atual é inválida pra essa ação."),
    ID_NOT_FOUND("O id informado não foi encontrado, verifique se está correto."),
    BAD_REQUEST("Verifique se todos os campos estão preenchidos corretamente."),
    FILE_NOT_FOUND("O arquivo não foi encontrado ou não é válido."),
    METHOD_NOT_ALLOWED("Verifique se o verbo HTTP utilizado está correto.");

    private String message;
}
