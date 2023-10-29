package com.br.tcc.bfn.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class UploadImageProfileRequest implements Serializable {

    private MultipartFile[] files;

    public UploadImageProfileRequest() {
    }

    public MultipartFile[] getFile() {
        return files;
    }

    public void setFile(MultipartFile[] files) {
        this.files = files;
    }
}
