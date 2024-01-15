package nnu.edu.schedule.service.impl;

import com.alibaba.fastjson2.JSONObject;
import nnu.edu.schedule.dao.pg.PgFlowMapper;
import nnu.edu.schedule.dao.pg.PgTideMapper;
import nnu.edu.schedule.dao.sqlite.SqliteFlowMapper;
import nnu.edu.schedule.dao.sqlite.SqliteTideMapper;
import nnu.edu.schedule.pojo.Flow;
import nnu.edu.schedule.pojo.Tide;
import nnu.edu.schedule.service.TransferService;
import nnu.edu.schedule.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2024/01/15/14:06
 * @Description:
 */
@Service
public class TransferServiceImpl implements TransferService {
    @Autowired
    SqliteFlowMapper sqliteFlowMapper;

    @Autowired
    PgFlowMapper pgFlowMapper;

    @Autowired
    SqliteTideMapper sqliteTideMapper;

    @Autowired
    PgTideMapper pgTideMapper;

    @Value("${databaseSelect}")
    String databaseSelect;

    @Value("${dateAddress}")
    String dateAddress;

    @Override
    public JSONObject transferData(String startTime, String endTime) {
        JSONObject dateJson = FileUtil.readJson(dateAddress);
        dateJson.put("transferDate", endTime);
        FileUtil.writeJson(dateAddress, dateJson);
        JSONObject res = new JSONObject();
        List<Tide> tideList;
        List<Flow> flowList;
        if (databaseSelect.equals("sqlite")) {
            tideList = sqliteTideMapper.selectDataByStartTimeAndEndTime(startTime, endTime);
            flowList = sqliteFlowMapper.selectDataByStartTimeAndEndTime(startTime, endTime);
        } else {
            tideList = pgTideMapper.selectDataByStartTimeAndEndTime(startTime, endTime);
            flowList = pgFlowMapper.selectDataByStartTimeAndEndTime(startTime, endTime);
        }
        res.put("tide", tideList);
        res.put("flow", flowList);
        return res;
    }
}
