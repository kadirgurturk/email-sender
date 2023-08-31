package com.kadirgurturk.emailSender.buisness.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@SuperBuilder
public class HttpResponse {
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String message;
    protected String DeveloperMessage;
    protected String path;
    protected String requestMethod;
    protected Map<?, ?> data;
}
