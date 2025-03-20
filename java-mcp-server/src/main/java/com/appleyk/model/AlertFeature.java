package com.appleyk.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/20:15:16
 * @description 告警要素
 */
@Data
@NoArgsConstructor
public class AlertFeature {
    private String event;
    private String areaDesc;
    private String severity;
    private String status;
    private String headline;
    public String toDesc(){
        List<String> result = new ArrayList<>();
        result.add("Event :"+(event == null ? "Unknown" : event ));
        result.add("AreaDesc :"+(areaDesc == null ? "Unknown" : areaDesc ));
        result.add("Severity :"+(severity == null ? "Unknown" : severity ));
        result.add("Status :"+(status == null ? "Unknown" : event ));
        result.add("Headline :"+(headline == null ? "No headline" : headline ));
        result.add("---");
        return String.join("",result);
    }
}
