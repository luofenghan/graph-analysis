package com.analysis.graph.web.library.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Created by cwc on 2017/5/6 0006.
 */
public class JsonUtils {
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteEnumUsingToString);
    }

    public static JSONObject toJsonObject(String json) {
        return JSON.parseObject(json);
    }

    public static <T> T toJavaObject(String json, TypeReference<T> typeReference) {
        if (Objects.isNull(json)) {
            return null;
        }
        return toJavaObject(new StringReader(json), typeReference);
    }

    private static <T> T toJavaObject(Reader reader, TypeReference<T> typeReference) {
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

    public static <T> T toJavaObject(InputStream in, TypeReference<T> typeReference) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
        return toJavaObject(reader, typeReference);
    }

}
