package com.example.team1_be.utils.errors.exception;

import org.springframework.http.HttpStatus;

import com.example.team1_be.utils.ApiUtils;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}

	public ApiUtils.ApiResult<?> body() {
		return ApiUtils.error(getMessage(), HttpStatus.NOT_FOUND);
	}

	public HttpStatus status() {
		return HttpStatus.NOT_FOUND;
	}
}