package com.appleyk.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/19:13:40
 * @description 告警响应对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertsResponse {
    private List<Map<String,String>> features;
}
