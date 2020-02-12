package com.murphy.community.controller;

import com.murphy.community.dto.FileDTO;
import com.murphy.community.provider.QcloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * FileController
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/12 10:31 下午
 */

@Controller
public class FileController {
    @Autowired
    QcloudProvider qcloudProvider;

    @ResponseBody
    @PostMapping("/file/upload")
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        FileDTO fileDTO = new FileDTO();
        String url = qcloudProvider.upload(file);
        fileDTO.setSuccess(1);
        fileDTO.setUrl(url);
        return fileDTO;
    }
}
