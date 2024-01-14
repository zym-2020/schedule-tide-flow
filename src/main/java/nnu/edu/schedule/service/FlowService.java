package nnu.edu.schedule.service;

import nnu.edu.schedule.pojo.Flow;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2024/01/14/23:05
 * @Description:
 */
public interface FlowService {
    List<Flow> getData(String stationId, String time);

    List<Flow> getData(String time);

    List<Flow> getData(String stationId, String startTime, String endTime);
}
