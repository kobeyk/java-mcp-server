package com.appleyk.service;

import com.appleyk.model.PointsResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/19:13:10
 * @description
 */
@Service
public class WeatherService {

    private final RestClient restClient;

    public WeatherService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.weather.gov")
                .defaultHeader("Accept", "application/geo+json")
                .defaultHeader("User-Agent", "WeatherApiClient/1.0 (your@email.com)")
                .build();
    }

    /**获取特定纬度/经度位置的天气预报。*/
    @Tool(description = "Get weather forecast for a specific latitude/longitude")
    public List<Map<String,String>> getWeatherForecastByLocation(double latitude, double longitude) {
        // Returns detailed forecast including:
        // - Temperature and unit
        // - Wind speed and direction
        // - Detailed forecast description
        PointsResponse pointsData = restClient.get().uri(String.format("/points/%.4f,%.4f", latitude, longitude)).retrieve().body(PointsResponse.class);
        List<Map<String,String>> content = new ArrayList<>();
        if (pointsData == null){
            String str = String.format("Failed to retrieve grid point data for coordinates: %f, %f. This location may not be supported by the NWS API (only US locations are supported).",latitude,longitude);
            return buildContent("text",str);
        }
        String forecastUrl = pointsData.getProperties().get("forecast");
        return buildContent("text",forecastUrl);
    }

    /**获取某美国州的天气预警信息。*/
    @Tool(description = "Get weather alerts for a US state")
    public String getAlerts(@ToolParam(description = "Two-letter US state code (e.g. CA, NY") String state) {
        // Returns active alerts including:
        // - Event type
        // - Affected area
        // - Severity
        // - Description
        // - Safety instructions
        return "";
    }

    public Map<String,String> buildMap(String type,String text){
        Map<String,String> data = new HashMap<>();
        data.put("type",type);
        data.put("text",text);
        return data;
    }

    public List<Map<String,String>> buildContent(String type,String text){
        List<Map<String,String>> content = new ArrayList<>();
        Map<String,String> data = new HashMap<>();
        data.put("type",type);
        data.put("text",text);
        content.add(data);
        return content;
    }
}
