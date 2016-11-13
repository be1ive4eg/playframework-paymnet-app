package com.hiddensign.web.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import play.libs.Json;

/**
 * @author Nikolay Denisenko
 * @version 2015/03/15
 */
@Singleton
public class JsonUtils {

    private static final JsonNode emptyNode = Json.newObject();

    public static JsonNode emptyResponse() {
        return emptyNode;
    }

    public static JsonNode response(String s) {
        return Json.newObject().put("data", s);
    }

    public static JsonNode response(Object o) {
        return Json.toJson(o);
    }

}
