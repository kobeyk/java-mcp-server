package com.appleyk.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class PointsResponse {
    private Map<String,String> properties;
}
