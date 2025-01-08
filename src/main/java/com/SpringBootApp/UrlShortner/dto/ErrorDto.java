package com.SpringBootApp.UrlShortner.dto;

public class ErrorDto {

    private String errorMessage;

    public ErrorDto() {
    }

    public ErrorDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
