package com.example.myapplication.vkapi;

import java.util.Map;

public interface IVkApiBuilder {
    String buildUrl(String pMethod);
    String buildUrl(String pMethod, Map<String,String> pParameters);

}
