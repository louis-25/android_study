package com.wearweatherapp;

public class statusResult {
    private String status;
    private String result;
    private String nickName;

    public statusResult(String status, String result) {
        this.status = status;
        this.result = result;
        this.nickName = nickName;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "statusResult{" +
                "status='" + status + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
