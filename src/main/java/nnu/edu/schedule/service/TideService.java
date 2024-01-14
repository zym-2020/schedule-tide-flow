package nnu.edu.schedule.service;

import nnu.edu.schedule.pojo.Flow;
import nnu.edu.schedule.pojo.Tide;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2024/01/14/23:06
 * @Description:
 */
public interface TideService {
    List<Tide> getData(String stationId, String time);

    List<Tide> getData(String time);

    List<Tide> getData(String stationId, String startTime, String endTime);
}
