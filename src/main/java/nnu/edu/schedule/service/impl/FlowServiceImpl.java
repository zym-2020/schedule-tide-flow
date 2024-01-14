package nnu.edu.schedule.service.impl;

import nnu.edu.schedule.dao.pg.PgFlowMapper;
import nnu.edu.schedule.dao.sqlite.SqliteFlowMapper;
import nnu.edu.schedule.pojo.Flow;
import nnu.edu.schedule.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2024/01/14/23:05
 * @Description:
 */
@Service
public class FlowServiceImpl implements FlowService {
    @Autowired
    SqliteFlowMapper sqliteFlowMapper;

    @Autowired
    PgFlowMapper pgFlowMapper;

    @Value("${databaseSelect}")
    String databaseSelect;

    @Override
    public List<Flow> getData(String stationId, String time) {
        if (databaseSelect.equals("sqlite")) {
            return sqliteFlowMapper.selectDataByStationIdAndTime(stationId, time);
        } else {
            return pgFlowMapper.selectDataByStationIdAndTime(stationId, time);
        }
    }

    @Override
    public List<Flow> getData(String time) {
        if (databaseSelect.equals("sqlite")) {
            return sqliteFlowMapper.selectAllDataByTime(time);
        } else {
            return pgFlowMapper.selectAllDataByTime(time);
        }
    }

    @Override
    public List<Flow> getData(String stationId, String startTime, String endTime) {
        if (databaseSelect.equals("sqlite")) {
            return sqliteFlowMapper.selectDataByStationIdAndStartTimeAndEndTime(stationId, startTime, endTime);
        } else {
            return pgFlowMapper.selectDataByStationIdAndStartTimeAndEndTime(stationId, startTime, endTime);
        }
    }
}
