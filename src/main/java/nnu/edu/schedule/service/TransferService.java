package nnu.edu.schedule.service;

import com.alibaba.fastjson2.JSONObject;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2024/01/15/14:06
 * @Description:
 */
public interface TransferService {
    JSONObject transferData(String startTime, String endTime);

    void push();
}
