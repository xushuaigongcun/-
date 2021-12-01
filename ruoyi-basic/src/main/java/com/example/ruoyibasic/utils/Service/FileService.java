package com.example.ruoyibasic.utils.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xushuai
 * @date 2021年12月01日 10:30 下午
 */
public interface FileService {
    void onlinePreview(String url, HttpServletResponse response) throws Exception;
}
