package com.ws.controller;

import com.ws.service.FileOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value = "/download")
@RestController
public class FileDownloadController {

    @Autowired
    private FileOperationsService fileOperationsService;

    @GetMapping(value = "/singleFile/{fileName}")
    public void downloadFile(HttpServletResponse response , @PathVariable(value = "fileName") String fileName) throws IOException {
        fileOperationsService.fileDownload(response, fileName);
    }

}
