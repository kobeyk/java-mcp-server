package com.appleyk.model.response;

import com.appleyk.model.ForecastPeriod;

import java.util.Map;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/19:13:42
 * @description 天气预报响应对象
 */
public class ForecastResponse {
    private Map<String, ForecastPeriod[]> properties;
}
