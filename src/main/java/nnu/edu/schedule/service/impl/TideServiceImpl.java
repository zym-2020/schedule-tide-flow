package nnu.edu.schedule.service.impl;

import nnu.edu.schedule.dao.pg.PgTideMapper;
import nnu.edu.schedule.dao.sqlite.SqliteTideMapper;
import nnu.edu.schedule.pojo.Tide;
import nnu.edu.schedule.service.TideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2024/01/14/23:06
 * @Description:
 */
@Service
public class TideServiceImpl implements TideService {
    @Autowired
    SqliteTideMapper sqliteTideMapper;

    @Autowired
    PgTideMapper pgTideMapper;

    @Value("${databaseSelect}")
    String databaseSelect;

    @Override
    public List<Tide> getData(String stationId, String time) {
        if (databaseSelect.equals("sqlite")) {
            return sqliteTideMapper.selectDataByStationIdAndTime(stationId, time);
        } else {
            return pgTideMapper.selectDataByStationIdAndTime(stationId, time);
        }
    }

    @Override
    public List<Tide> getData(String time) {
        if (databaseSelect.equals("sqlite")) {
            return sqliteTideMapper.selectAllDataByTime(time);
        } else {
            return pgTideMapper.selectAllDataByTime(time);
        }
    }

    @Override
    public List<Tide> getData(String stationId, String startTime, String endTime) {
        if (databaseSelect.equals("sqlite")) {
            return sqliteTideMapper.selectDataByStationIdAndStartTimeAndEndTime(stationId, startTime, endTime);
        } else {
            return pgTideMapper.selectDataByStationIdAndStartTimeAndEndTime(stationId, startTime, endTime);
        }
    }
}
