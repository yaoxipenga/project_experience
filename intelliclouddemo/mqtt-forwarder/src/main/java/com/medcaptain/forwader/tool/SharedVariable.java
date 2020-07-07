package com.medcaptain.forwader.tool;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SharedVariable {
    public static Map<String, String> deviceResponseTopicList = new ConcurrentHashMap<>();
    public static Boolean isConnecting = false;
}
