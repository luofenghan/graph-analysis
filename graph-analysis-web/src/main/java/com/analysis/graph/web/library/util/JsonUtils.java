package com.analysis.graph.web.library.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by cwc on 2017/5/6 0006.
 */
public class JsonUtils {
    public static <T> T jsonToJavaObject(String json, TypeReference<T> typeReference) {
        StringReader stringReader = new StringReader(json);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        try {
            return mapper.readValue(stringReader, typeReference);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T parseJavaFromJsonStream(InputStream in, TypeReference<T> typeReference) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        try {
            return mapper.readValue(reader, typeReference);
        } catch (IOException e) {
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
            }
        }
    }

}
