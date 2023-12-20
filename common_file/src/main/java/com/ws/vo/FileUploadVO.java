package com.ws.vo;

import lombok.Data;

@Data
public class FileUploadVO {


    /**
     * 是否成功
     */
    private Boolean saveSuccess;

    /**
     * 下载网址
     */
    private String downloadUrl;

    /**
     * 保存路径
     */
    private String savePath;

}
