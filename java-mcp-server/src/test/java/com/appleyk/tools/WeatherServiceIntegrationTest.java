package com.appleyk.tools;

import com.appleyk.service.WeatherService;
import com.appleyk.utils.JsonUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/20:11:56
 * @description 单元测试
 * @note
 * 解决OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
 * 在vm参数添加：-Xshare:off
 */
@SpringBootTest
@ActiveProfiles("integration-test")
@Tag("integration")
public class WeatherServiceIntegrationTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    void getForecast(){
        double lat = 39.7456f;
        double lon = -97.0892;
        List<Map<String, String>> result = weatherService.getWeatherForecastByLocation(lat, lon);
        System.out.println(JsonUtil.objectToJson(result));
    }

    @Test
    void getAlert(){
        /**只支持简写，NY表示纽约*/
        String state = "NY";
        List<Map<String, String>> result = weatherService.getAlerts(state);
        System.out.println(JsonUtil.objectToJson(result));
    }
}
