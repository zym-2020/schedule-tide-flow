package nnu.edu.schedule.controller;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.schedule.common.result.JsonResult;
import nnu.edu.schedule.common.result.ResultUtils;
import nnu.edu.schedule.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
