package com.michaelzhu.exchange;

public record ApiErrorResponse(ApiError error, String data, String message) {
}
