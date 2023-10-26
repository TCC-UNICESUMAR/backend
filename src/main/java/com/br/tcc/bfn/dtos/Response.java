package com.br.tcc.bfn.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class Response<T> extends RepresentationModel<Response<T>> implements Serializable {

    private int statusCode;

    private T body;

    private Long timeStamp;

    private String error;

    public Response() {
        this.timeStamp = System.currentTimeMillis();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}