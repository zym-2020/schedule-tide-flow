package nnu.edu.schedule.controller;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.schedule.common.result.JsonResult;
import nnu.edu.schedule.common.result.ResultUtils;
import nnu.edu.schedule.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2024/01/15/14:05
 * @Description:
 */
@RestController
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    TransferService transferService;

    @RequestMapping(value = "/transferData", method = RequestMethod.POST)
    public JsonResult transferData(@RequestBody JSONObject jsonObject) {
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        return ResultUtils.success(transferService.transferData(startTime, endTime));
    }

    @RequestMapping(value = "/push/{startTimestamp}/{endTimestamp}", method = RequestMethod.POST)
    public JsonResult push(@PathVariable Long startTimestamp, @PathVariable Long endTimestamp) {
        Instant start = Instant.ofEpochSecond(startTimestamp);
        // 将Instant对象转换为本地日期时间对象
        LocalDateTime startDateTime = LocalDateTime.ofInstant(start, ZoneId.systemDefault());
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化日期时间对象为字符串
        String startTime = startDateTime.format(formatter);
        Instant end = Instant.ofEpochSecond(startTimestamp);
        // 将Instant对象转换为本地日期时间对象
        LocalDateTime endDateTime = LocalDateTime.ofInstant(end, ZoneId.systemDefault());
        // 格式化日期时间对象为字符串
        String endTime = endDateTime.format(formatter);
        transferService.push(startTime, endTime);
        return ResultUtils.success();
    }
}
