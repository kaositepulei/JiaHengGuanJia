package com.example.myapplication.entity;

public class LoginResponse {

    /**
     * msg : success
     * code : 0
     * expire : 604800
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2IiwiaWF0IjoxNTkyNDg2OTQzLCJleHAiOjE1OTMwOTE3NDN9.f5sxyG60GyDlj0FcZEmPAADiLHX_pATrvicxbADqvRqYurYQC5s0KAjw5XgHS4gpk-qUSwWtcJpY_nJjYf_2Dw
     */

    private String status;
    private String msg;

    public LoginResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
