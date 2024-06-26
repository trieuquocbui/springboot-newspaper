package com.bqt.newspaper.configuration;

import com.bqt.newspaper.exception.ErrorResponse;
import com.bqt.newspaper.exception.NewsPaperGlobalException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {

        return extractNewsPaperGlobalException(response);
    }

    private NewsPaperGlobalException extractNewsPaperGlobalException(Response response) {

        try {
            NewsPaperGlobalException newsPaperGlobalException = objectMapper.readValue(response.body().asInputStream(), NewsPaperGlobalException.class);
            newsPaperGlobalException.setHttpStatus(HttpStatus.valueOf(response.status()));
            return newsPaperGlobalException;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
