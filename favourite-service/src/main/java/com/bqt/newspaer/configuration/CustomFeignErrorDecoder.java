package com.bqt.newspaer.configuration;

import com.bqt.newspaer.exception.NewsPaperGlobalException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        NewsPaperGlobalException newsPaperGlobalException = extractNewsPaperGlobalException(response);

        switch (response.status()){
            case 400:
                return newsPaperGlobalException;
            default:
                return new Exception("Common Feign Exception");
        }
    }

    private NewsPaperGlobalException extractNewsPaperGlobalException(Response response) {
        NewsPaperGlobalException exceptionMessage = null;
        Reader reader = null;
        //capturing error message from response body.
        try {
            reader = response.body().asReader(StandardCharsets.UTF_8);
            String result = IOUtils.toString(reader);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            exceptionMessage = mapper.readValue(result,
                    NewsPaperGlobalException.class);
        } catch (IOException e) {
            log.error("IO Exception on reading exception message feign client" + e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error("IO Exception on reading exception message feign client" + e);
            }
        }
        return exceptionMessage;
    }
}
