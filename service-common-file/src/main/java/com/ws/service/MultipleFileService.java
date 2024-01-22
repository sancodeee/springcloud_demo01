package com.ws.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MultipleFileService {

    void compress(List<Map<String, String>> filePaths, String zipFilePath, Boolean keepDirStructure);


}
