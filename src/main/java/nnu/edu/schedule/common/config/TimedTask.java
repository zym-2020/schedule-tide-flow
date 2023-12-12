package nnu.edu.schedule.common.config;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.schedule.service.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/12/21:39
 * @Description:
 */
@Component
public class TimedTask {
    @Autowired
    FetchService fetchService;

    String url = "http://10.6.53.37:83/apiJson/get";
    String token = "fOj!X@1$knK0SrDBqproI4DQNJSLKXY";

    @Scheduled(cron = "0 15 * * * ?")
    public void fetch() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastHourDateTime = currentTime.minusHours(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeParam = currentTime.format(formatter) + "," + lastHourDateTime.format(formatter);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        JSONObject tideJsonChild = new JSONObject();
        tideJsonChild.put("STCD{}", new String[]{"60116700CWF", "60117840", "60117400", "60117820"});
        tideJsonChild.put("TM%", timeParam);
        tideJsonChild.put("@column", "STCD:id,TM,TDZ");
        tideJsonChild.put("@order", "TM-");
        JSONObject tideJson = new JSONObject();
        tideJson.put("ST_TIDE_R", tideJsonChild);
        tideJson.put("page", 0);
        tideJson.put("count", 100);
        JSONObject tideRequestBody = new JSONObject();
        tideRequestBody.put("ST_TIDE_R[]", tideJson);
        fetchService.fetchTide(url, headers, tideRequestBody.toJSONString());

        JSONObject flowJsonChild = new JSONObject();
        flowJsonChild.put("STCD{}", new String[]{"60117400"});
        flowJsonChild.put("TM%", timeParam);
        flowJsonChild.put("@column", "STCD:id,TM,Z,Q");
        flowJsonChild.put("@order", "TM-");
        JSONObject flowJson = new JSONObject();
        tideJson.put("ST_RIVER_R", tideJsonChild);
        tideJson.put("page", 0);
        tideJson.put("count", 100);
        JSONObject flowRequestBody = new JSONObject();
        flowRequestBody.put("ST_RIVER_R[]", flowJson);
        fetchService.fetchTide(url, headers, flowRequestBody.toJSONString());
    }
}
