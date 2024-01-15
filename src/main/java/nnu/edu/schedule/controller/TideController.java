package nnu.edu.schedule.controller;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.schedule.common.exception.MyException;
import nnu.edu.schedule.common.result.JsonResult;
import nnu.edu.schedule.common.result.ResultEnum;
import nnu.edu.schedule.common.result.ResultUtils;
import nnu.edu.schedule.service.TideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2024/01/14/23:04
 * @Description:
 */
@RestController
@RequestMapping("/tide")
public class TideController {
    @Autowired
    TideService tideService;

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public JsonResult getData(@RequestBody JSONObject jsonObject) {
        String stationId = jsonObject.getString("stationId");
        String time = jsonObject.getString("time");
        String startTime = jsonObject.getString("startTime");
        String endTime = jsonObject.getString("endTime");
        if (time != null && stationId == null && startTime == null && endTime == null)
            return ResultUtils.success(tideService.getData(time));
        else if (time != null && stationId != null && startTime == null && endTime == null)
            return ResultUtils.success(tideService.getData(stationId, time));
        else if (time == null && stationId != null && startTime != null && endTime != null)
            return ResultUtils.success(tideService.getData(stationId, startTime, endTime));
        else throw new MyException(ResultEnum.PARAMETER_ERROR);
    }

}
