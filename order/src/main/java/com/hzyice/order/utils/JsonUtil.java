package com.hzyice.order.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * Created by 廖师兄
 * 2018-02-21 10:40
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*使用的是： net.sf.json.JSONObject  包下操作的*/
    /*json转换成对象*/
    public static Object fromJson(String json, Class t) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        return jsonObject.toBean(jsonObject, t);
    }


    /*使用objectMapper来操作json转换成对象*/
    public static Object fromJsonObjctMapper(String json, Class classType) {
        try {
            return objectMapper.readValue(json, classType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*json转换成List对象*/
    public static Object fromJsonToList(String json, TypeReference typeReference){
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        /*
        * 转换案例
        *
        * fromJsonToList(json, new TypeReference<List<Object>>() {
        });
        * */
    }
}
