package com.dekaveenvelopamentos.dekave.exception;

import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(value = { NoSuchElementException.class, EntityNotFoundException.class,
			EmptyResultDataAccessException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage IdNotFoundException() {

		ErrorMessageEnum message = ErrorMessageEnum.ID_NOT_FOUND;

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				message.getMessage());
		return errorMessage;
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class, MissingRequestHeaderException.class,
			MissingServletRequestPartException.class, HttpMessageNotReadableException.class,
			MethodArgumentTypeMismatchException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage NotBlankException() {

		ErrorMessageEnum message = ErrorMessageEnum.BAD_REQUEST;

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				new Date(),
				message.getMessage());
		return errorMessage;
	}

	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ErrorMessage MethodNotAllowedException() {

		ErrorMessageEnum message = ErrorMessageEnum.METHOD_NOT_ALLOWED;

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.METHOD_NOT_ALLOWED.value(),
				new Date(),
				message.getMessage());
		return errorMessage;
	}

	@ExceptionHandler(value = { IOException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage IOException() {

		ErrorMessageEnum message = ErrorMessageEnum.FILE_NOT_FOUND;

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				new Date(),
				message.getMessage());
		return errorMessage;
	}

}
