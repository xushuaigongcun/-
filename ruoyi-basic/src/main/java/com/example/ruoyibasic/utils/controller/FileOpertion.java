package com.example.ruoyibasic.utils.controller;

import com.example.ruoyibasic.utils.Service.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xushuai
 * @date 2021年12月01日 10:28 下午
 */
public class FileOpertion {
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "系统文件在线预览接口")
    @PostMapping("/api/file/onlinePreview")
    public void onlinePreview(@RequestParam("url") String url, HttpServletResponse response) throws Exception{
        fileService.onlinePreview(url,response);
    }
}
