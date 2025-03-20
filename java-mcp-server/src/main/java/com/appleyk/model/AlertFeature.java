package com.appleyk.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author appleyk
 * @version v1.0.0
 * @date created on 2025/3/20:15:16
 * @description 预警要素模型
 */
@Data
@NoArgsConstructor
public class AlertFeature {
    /**事件名称（洪水警报）*/
    private String event;
    /**影响区域描述（如：杰斐逊,纽约;刘易斯,纽约）*/
    private String areaDesc;
    /**严重程度：低、中、高...eg（如：十分严重）*/
    private String severity;
    /**状态：（如：洪水警报）*/
    private String status;
    /**截止时间（如：美国东部时间3月19日下午4点15分至美国东部时间3月20日上午8点，纽约布法罗国家气象局发布洪水预警）*/
    private String headline;
    public String toDesc(){
        List<String> result = new ArrayList<>();
        result.add("Event :"+(event == null ? "Unknown" : event ));
        result.add("\nAreaDesc :"+(areaDesc == null ? "Unknown" : areaDesc ));
        result.add("\nSeverity :"+(severity == null ? "Unknown" : severity ));
        result.add("\nStatus :"+(status == null ? "Unknown" : event ));
        result.add("\nHeadline :"+(headline == null ? "No headline" : headline ));
        result.add("\n---");
        return String.join("",result);
    }
}
