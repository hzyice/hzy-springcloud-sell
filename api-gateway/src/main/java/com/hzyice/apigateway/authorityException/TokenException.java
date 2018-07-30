package com.hzyice.apigateway.authorityException;

public class TokenException extends RuntimeException {

    public void TokenException() {
       // return "staticHTML:error.html";
    }

    public TokenException(String s) {
        super(s);
    }



}
