package com.tim20.rivera.dto;

public class TokenStateDTO {


    private String accessToken;
    private Long expiresIn;

    public TokenStateDTO() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public TokenStateDTO(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

}
