package com.gonyb.share.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.DelegatingServletInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * request wrapper: 可重复读取request.getInputStream
 */
public class RepeatedlyReadRequestWrapper extends HttpServletRequestWrapper  {
    private static final Logger logger = LoggerFactory.getLogger(ControllerLogsInterceptor.class);
    private static final int BUFFER_START_POSITION = 0;

    private static final int CHAR_BUFFER_LENGTH = 1024;

    /**
     * input stream 的buffer
     */
    private final String body;

    /**
     * @param request {@link javax.servlet.http.HttpServletRequest} object.
     */
    public RepeatedlyReadRequestWrapper(HttpServletRequest request) {
        super(request);

        StringBuilder stringBuilder = new StringBuilder();

        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            logger.error("Error reading the request body…", e);
        }
        if (inputStream != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                char[] charBuffer = new char[CHAR_BUFFER_LENGTH];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, BUFFER_START_POSITION, bytesRead);
                }
            } catch (IOException e) {
                logger.error("Fail to read input stream",e);
            }
        } else {
            stringBuilder.append("");
        }
        body = stringBuilder.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new DelegatingServletInputStream(byteArrayInputStream);
    }
}
