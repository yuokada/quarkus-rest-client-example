package io.github.yuokada.rest.service;

public record ErrorMessage(
    String message,
    String detail
) {

    public ErrorMessage {
        //  Throw IllegalArgumentException if message is null or blank
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("message is null or blank");
        }
    }
}
