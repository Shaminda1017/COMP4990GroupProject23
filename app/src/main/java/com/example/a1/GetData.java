package com.example.a1;

public class GetData {

    public String fileName;
    public String url;

    public GetData(){

    }
    public GetData(String fileName, String url){
        this.fileName = fileName;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
