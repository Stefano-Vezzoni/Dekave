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

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				ErrorMessageEnum.ID_NOT_FOUND.getMessage());
		return errorMessage;
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class, MissingRequestHeaderException.class,
			MissingServletRequestPartException.class, HttpMessageNotReadableException.class,
			MethodArgumentTypeMismatchException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage NotBlankException() {

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				new Date(),
				ErrorMessageEnum.BAD_REQUEST.getMessage());
		return errorMessage;
	}

	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ErrorMessage MethodNotAllowedException() {

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.METHOD_NOT_ALLOWED.value(),
				new Date(),
				ErrorMessageEnum.METHOD_NOT_ALLOWED.getMessage());
		return errorMessage;
	}

	@ExceptionHandler(value = { IOException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage IOException() {

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				new Date(),
				ErrorMessageEnum.FILE_NOT_FOUND.getMessage());
		return errorMessage;
	}

	@ExceptionHandler(value = { ReorderPositionException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage ReorderPositionException() {

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				new Date(),
				ErrorMessageEnum.REORDER_POSITION.getMessage());
		return errorMessage;
	}

	@ExceptionHandler(value = { ReorderActionException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage ReorderActionException() {

		ErrorMessage errorMessage = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				new Date(),
				ErrorMessageEnum.REORDER_ACTION.getMessage());
		return errorMessage;
	}

}
