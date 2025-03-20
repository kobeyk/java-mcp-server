package com.appleyk.service;

import com.appleyk.model.AlertFeature;
import com.appleyk.model.ForecastPeriod;
import com.appleyk.model.response.AlertsResponse;
import com.appleyk.model.response.PointsResponse;
import com.appleyk.model.response.ForecastResponse;
import com.appleyk.utils.JsonUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.*;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/19:13:10
 * @description 天气预报Service
 *
 * 案例：
 *
 * Get请求：
 * Article article = restClient.get()
 *     .uri("http://example.com/api/articles/{articleId}")
 *     .retrieve()
 *     .onStatus(HttpStatus::isNotFound, (request, response) -> {
 *         throw new ArticleNotFoundException("Article not found: " + articleId);
 *     })
 * .body(Article.class);
 *
 * Post请求：
 *
 * Article article = new Article("New Article");
 * ResponseEntity<Void> response = restClient.post()
 *     .uri("http://example.com/api/articles")
 *     .contentType(MediaType.APPLICATION_JSON)
 *     .body(article, Void.class)
 *     .exchange();
 */
@Service
public class WeatherService {

    /**3.2新推出来的特性*/
    private final RestClient restClient;

    /**
     * 官方案例：https://www.weather.gov/documentation/services-web-api
     */
    public WeatherService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.weather.gov")
                .defaultHeader("Accept", "application/geo+json") // application/geo+json‌是一种用于表示地理数据的MIME类型，它是基于JSON（JavaScript Object Notation）格式的扩展，说白了就是geojson要素格式。
                .defaultHeader("User-Agent", "WeatherApiClient/1.0 (yukun24@126.com)")
                .build();
    }

    /**
     * 获取特定纬度/经度位置的天气预报。
     * To obtain the grid forecast for a point location, use the /points endpoint to retrieve the current grid forecast endpoint by coordinates:
     * https://api.weather.gov/points/{latitude},{longitude}
     * For example: https://api.weather.gov/points/39.7456,-97.0892
     */
    @Tool(description = "Get weather forecast for a specific latitude/longitude")
    public List<Map<String,String>> getWeatherForecastByLocation(double latitude, double longitude) {
        /**
         * Returns detailed forecast including:
         * Temperature and unit
         * Wind speed and direction
         * Detailed forecast description
         */
        PointsResponse pointsData = restClient.get().uri(String.format("/points/%.4f,%.4f", latitude, longitude)).retrieve().body(PointsResponse.class);
        if (pointsData == null){
            String str = String.format("Failed to retrieve grid point data for coordinates: %f, %f. This location may not be supported by the NWS API (only US locations are supported).",latitude,longitude);
            return buildContent("text",str);
        }
        /**获取天气预报的服务地址*/
        String forecastUrl = pointsData.getProperties().containsKey(PointsResponse.FORECAST) ? pointsData.getProperties().get(PointsResponse.FORECAST).toString() : null;
        if (forecastUrl == null){
            return buildContent("text","Failed to get forecast URL from grid point data");
        }
        ForecastResponse forecastData = restClient.get().uri(forecastUrl).retrieve().body(ForecastResponse.class);
        List<LinkedHashMap<String,Object>> periods =forecastData.getProperties().containsKey(ForecastPeriod.PERIODS) ?  (List<LinkedHashMap<String,Object>>)forecastData.getProperties().get(ForecastPeriod.PERIODS):null;
        if (periods == null){
            return buildContent("text","No forecast periods available");
        }

        String[] result = new String[periods.size()];
        /**解析periods，遍历封装txt*/
        int index = 0;
        for (LinkedHashMap periodMap : periods) {
            ForecastPeriod period = JsonUtil.jsonToPojo(JsonUtil.objectToJson(periodMap),ForecastPeriod.class);
            result[index] = period.toDesc();
            index++;
        }
        String forecastText  = String.format("Forecast for %f,%f : \n\n %s",latitude,longitude,String.join("\n",result));
        return buildContent("text",forecastText);
    }

    /**
     * 获取某美国州的天气预警信息。
     * The API has a robust selection of filters for alerts. A common request is all active alerts for a state:
     * https://api.weather.gov/alerts/active?area={state}
     * For example: https://api.weather.gov/alerts/active?area=KS
     */
    @Tool(description = "Get weather alerts for a US state")
    public List<Map<String,String>> getAlerts(@ToolParam(description = "Two-letter US state code (e.g. CA, NY") String state) {
        // Returns active alerts including:
        // - Event type
        // - Affected area
        // - Severity
        // - Description
        // - Safety instructions
        state = state.toUpperCase();
        AlertsResponse alertData = restClient.get().uri(String.format("/alerts/active?area=%s",state)).retrieve().body(AlertsResponse.class);
        if (alertData == null){
            return buildContent("text","Failed to retrieve alerts data");
        }
        List<Map<String, Object>> features = alertData.getFeatures();
        if (features.size() == 0){
            return buildContent("text","No active alerts for " + state);
        }
        String[] result = new String[features.size()];
        int index = 0;
        for (Map<String, Object> featureMap : features) {
            AlertFeature feature = JsonUtil.jsonToPojo(JsonUtil.objectToJson(featureMap.get(AlertsResponse.PROPERTIES)),AlertFeature.class);
            result[index] = feature.toDesc();
            index++;
        }
        String alertsText   = String.format("Active alerts for  %s : \n\n %s",state,String.join("\n",result));
        return buildContent("text",alertsText);
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
