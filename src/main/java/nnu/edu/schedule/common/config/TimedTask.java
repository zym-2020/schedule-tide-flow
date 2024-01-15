package nnu.edu.schedule.common.config;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.schedule.service.FetchService;
import nnu.edu.schedule.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

    @Value("${jsonAddress}")
    String jsonAddress;

    @Value("${dateAddress}")
    String dateAddress;


    @Scheduled(cron = "0 30 * * * ?")
    public void fetch() {
        JSONObject dateJson = FileUtil.readJson(dateAddress);
        String lastDate = dateJson.getString("lastDate");
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeParam = lastDate + "," + currentTime.format(formatter);

        JSONObject config = FileUtil.readJson(jsonAddress);

        HttpHeaders tideHeaders = new HttpHeaders();
        JSONObject tideHeader = config.getJSONObject("tide").getJSONObject("header");
        if (tideHeader != null) {
            for (String key : tideHeader.keySet()) {
                tideHeaders.set(key, tideHeader.getString(key));
            }
        }

        JSONObject tideRequestBody = config.getJSONObject("tide").getJSONObject("body");
        JSONObject tideJsonChild = tideRequestBody.getJSONObject("ST_TIDE_R[]").getJSONObject("ST_TIDE_R");
        tideJsonChild.put("TM%", timeParam);
        fetchService.fetchTide(config.getJSONObject("tide").getString("url"), tideHeaders, tideRequestBody.toJSONString());

        HttpHeaders flowHeaders = new HttpHeaders();
        JSONObject flowHeader = config.getJSONObject("flow").getJSONObject("header");
        for (String key : flowHeader.keySet()) {
            flowHeaders.set(key, flowHeader.getString(key));
        }
        JSONObject flowRequestBody = config.getJSONObject("flow").getJSONObject("body");

        JSONObject flowJsonChild = flowRequestBody.getJSONObject("ST_RIVER_R[]").getJSONObject("ST_RIVER_R");
        flowJsonChild.put("TM%", timeParam);
        fetchService.fetchFlow(config.getJSONObject("flow").getString("url"), flowHeaders, flowRequestBody.toJSONString());

        dateJson.put("lastDate", currentTime.format(formatter));
        FileUtil.writeJson(dateAddress, dateJson);
    }

    /**
    * @Description:定时备份任务
    * @Author: Yiming
    * @Date: 2023/12/17
    */
    @Scheduled(cron = "0 0 0 1 3,6,9,12 ?")
//    @Scheduled(cron = "0 54 * * * ?")
    void backup() {
        JSONObject dateJson = FileUtil.readJson(dateAddress);
        String storageDate = dateJson.getString("storageDate");
        String transferDate = dateJson.getString("transferDate");

        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTime = LocalDateTime.parse(transferDate, originalFormatter);

        String tideTableName = "tide" + storageDate + "_" + dateTime.format(dateTimeFormatter);
        fetchService.backupTide(tideTableName, transferDate);

        String flowTableName = "flow" + storageDate + "_" + dateTime.format(dateTimeFormatter);
        fetchService.backupFlow(flowTableName, transferDate);

        dateJson.put("storageDate", dateTime.format(dateTimeFormatter));
        FileUtil.writeJson(dateAddress, dateJson);
    }
}
