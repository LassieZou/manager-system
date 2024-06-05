package org.qqz.common.utils;


import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;

public class Base64Utils {
    public static String encode(Object o){
        return Base64.encodeBase64String(JSON.toJSONBytes(o));
    }

    public static String decode(String base64){
        return new String(Base64.decodeBase64(base64));
    }

    public static void main(String[] args) {
        Map map = new HashMap<>();
        map.put("userId",123456);
        map.put("accountName","A");
        map.put("role","user");

        System.out.println(encode(map));
    }
}
