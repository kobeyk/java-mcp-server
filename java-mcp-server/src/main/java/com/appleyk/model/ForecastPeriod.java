package com.appleyk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/19:13:25
 * @description 天气预报时间段
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecastPeriod {
    private String name;
    private Double temperature;
    private String temperatureUnit;
    private String windSpeed;
    private String windDirection;
    private String shortForecast;
}
