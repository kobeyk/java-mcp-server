package com.appleyk.model.response;

import com.appleyk.model.ForecastPeriod;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/19:13:42
 * @description 天气预报响应对象
 */
@Data
@NoArgsConstructor
public class ForecastResponse {
    private Map<String, Object> properties;
}
