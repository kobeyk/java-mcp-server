package com.appleyk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/19:13:25
 * @description 天气预报时间段
 */
@Data
@NoArgsConstructor
public class ForecastPeriod {
    public final static String PERIODS = "periods";
    /**时刻名称（比如今天是周二，那就是夜间或白天，如果是明天，就周五 & 周五夜间等）*/
    private String name;
    /**温度*/
    private Integer temperature;
    /**温度单位*/
    private String temperatureUnit;
    /**风速*/
    private String windSpeed;
    /**风的方向*/
    private String windDirection;
    /**简短播报*/
    private String shortForecast;
    /**详细播报*/
    private String detailedForecast;

    public String toDesc(){
        List<String> result = new ArrayList<>();
        result.add(name == null ? "Unknown" : name + ":");
        result.add("Temperature:" + (temperature == null ? "Unknown" : temperature) + "°" + (temperatureUnit == null ? "F" : temperatureUnit));
        result.add("Wind:" + (windSpeed==null ? "UnKnown" : windSpeed) + " "+(windDirection == null ? "" : windDirection));
        result.add(shortForecast ==null ? "No forecast available" : shortForecast);
        result.add("---");
        return String.join("",result);
    }
}
