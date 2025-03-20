package com.appleyk.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/19:13:35
 * @description 基于经纬度坐标点获取天气的业务模型
 */
@Data
@NoArgsConstructor
public class PointsResponse {
    public final static String FORECAST = "forecast";
    private String id;
    private Map<String,Object> properties;
}
