package com.example.ruoyibasic.utils;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.io.*;
import java.lang.annotation.Inherited;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author xushuai
 * @date 2021年12月01日 10:17 下午
 */
/*
 *
 * @author xushuai
 * @date 2021/12/1 10:17 下午
 * @param null
 * @return 文件转换工具类
 */
public class FileConvertUtil {
    //默认文件转换后缀
    private static final String DEFAULT_SUFFIX = "pdf";

    private static final Integer OPENOFFIC_PORT = 8100;

    public static InputStream convertLocalefile(String sourcePath,String suffix) throws Exception {
        File file = new File(sourcePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        return covertCommonByStream(fileInputStream,suffix);

    }
    /**      * 方法描述  office文档转换为PDF(处理网络文件)      *      * @param netFileUrl 网络文件路径      * @param suffix     文件后缀      * @return InputStream 转换后文件输入流      */
    public static InputStream convertNetFile(String netFileUrl, String suffix) throws Exception {
        // 创建URL
        URL url = new URL(netFileUrl);
        // 试图连接并取得返回状态码
        URLConnection urlconn = url.openConnection();
        urlconn.connect();
        HttpURLConnection httpconn = (HttpURLConnection) urlconn;
        int httpResult = httpconn.getResponseCode();
        if (httpResult == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = urlconn.getInputStream();
            return covertCommonByStream(inputStream, suffix);
        }
        return null;
    }
    /**
     * 方法描述  将文件以流的形式转换
     *
     * @param inputStream 源文件输入流
     * @param suffix      源文件后缀
     * @return InputStream 转换后文件输入流
     */
    public static InputStream covertCommonByStream(InputStream inputStream, String suffix) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(OPENOFFIC_PORT);
        connection.connect();
        DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
        DocumentFormat targetFormat = formatReg.getFormatByFileExtension(DEFAULT_SUFFIX);
        DocumentFormat sourceFormat = formatReg.getFormatByFileExtension(suffix);
        converter.convert(inputStream, sourceFormat, out, targetFormat);
        connection.disconnect();
        return outputStreamConvertInputStream(out);
    }
    /**      * 方法描述 outputStream转inputStream      */
    public static ByteArrayInputStream outputStreamConvertInputStream(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos=(ByteArrayOutputStream) out;
        return new ByteArrayInputStream(baos.toByteArray());
    }
    public static void main(String[] args) throws IOException {
        //convertNetFile("http://172.16.10.21/files/home/upload/department/base/201912090541573c6abdf2394d4ae3b7049dcee456d4f7.doc", ".pdf");
        //convert("c:/Users/admin/Desktop/2.pdf", "c:/Users/admin/Desktop/3.pdf");
    }


}
