package com.br.tcc.bfn.dtos;

import org.springframework.web.multipart.MultipartFile;

public class RegisterDonationRequest {
    private String body;
    private MultipartFile[] files;

    public RegisterDonationRequest() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
