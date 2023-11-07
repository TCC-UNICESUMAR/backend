package com.br.tcc.bfn.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class UploadImageProfileRequest{

    private MultipartFile[] files;

    public UploadImageProfileRequest() {
    }

    public MultipartFile getFiles() {
        return files[0];
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
