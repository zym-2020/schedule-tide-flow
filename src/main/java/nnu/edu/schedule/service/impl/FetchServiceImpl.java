package nnu.edu.schedule.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import nnu.edu.schedule.dao.pg.PgFlowMapper;
import nnu.edu.schedule.dao.pg.PgTideMapper;
import nnu.edu.schedule.dao.sqlite.SqliteFlowMapper;
import nnu.edu.schedule.dao.sqlite.SqliteTideMapper;
import nnu.edu.schedule.pojo.Flow;
import nnu.edu.schedule.pojo.Tide;
import nnu.edu.schedule.service.FetchService;
import nnu.edu.schedule.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/12/21:42
 * @Description:
 */
@Service
@Slf4j
public class FetchServiceImpl implements FetchService {
    @Value("${databaseSelect}")
    String databaseSelect;

    @Autowired
    SqliteTideMapper sqliteTideMapper;

    @Autowired
    SqliteFlowMapper sqliteFlowMapper;

    @Autowired
    PgTideMapper pgTideMapper;

    @Autowired
    PgFlowMapper pgFlowMapper;

    @Override
    public void fetchTide(String url, HttpHeaders headers, String jsonBody) {
        JSONObject jsonObject = RequestUtil.postUtil(url, headers, jsonBody, JSONObject.class);
        if (jsonObject.getString("msg").equals("success")) {
            JSONArray jsonArray = jsonObject.getJSONArray("ST_TIDE_R[]");
            List<Tide> tideList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject tide = jsonArray.getJSONObject(i);
                tideList.add(new Tide(tide.getString("STCD"), tide.getString("TM"), tide.getDouble("TDZ")));
            }
            if (tideList.size() > 0) {
                if (databaseSelect.equals("sqlite")) sqliteTideMapper.insertValue(tideList);
                else pgTideMapper.insertValue(tideList);
            }
        } else log.error("tide请求错误");
    }

    @Override
    public void fetchFlow(String url, HttpHeaders headers, String jsonBody) {
        JSONObject jsonObject = RequestUtil.postUtil(url, headers, jsonBody, JSONObject.class);
        if (jsonObject.getString("msg").equals("success")) {
            JSONArray jsonArray = jsonObject.getJSONArray("ST_TIDE_R[]");
            List<Flow> flowList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject flow = jsonArray.getJSONObject(i);
                flowList.add(new Flow(flow.getString("STCD"), flow.getString("TM"), flow.getDouble("Z"), flow.getDouble("Q")));
            }
            if (flowList.size() > 0) {
                if (databaseSelect.equals("sqlite")) sqliteFlowMapper.insertValue(flowList);
                else pgFlowMapper.insertValue(flowList);
            }
        } else log.error("tide请求错误");
    }

    @Override
    public void backupTide(String tableName, String time) {
        if (databaseSelect.equals("sqlite")) {
            sqliteTideMapper.createTable(tableName);
            List<Tide> tideList = sqliteTideMapper.selectAllDataByTime(time);
            if (tideList.size() > 0) sqliteTideMapper.insertValueByTableName(tideList, tableName);
            sqliteTideMapper.deleteDataByTime(time);
        } else if (databaseSelect.equals("pg")) {
            pgTideMapper.createTable(tableName);
            List<Tide> tideList = pgTideMapper.selectAllDataByTime(time);
            if (tideList.size() > 0) pgTideMapper.insertValueByTableName(tideList, tableName);
            pgTideMapper.deleteDataByTime(time);
        }
    }

    @Override
    public void backupFlow(String tableName, String time) {
        if (databaseSelect.equals("sqlite")) {
            sqliteFlowMapper.createTable(tableName);
            List<Flow> flowList = sqliteFlowMapper.selectAllDataByTime(time);
            if (flowList.size() > 0) sqliteFlowMapper.insertValueByTableName(flowList, tableName);
            sqliteFlowMapper.deleteDataByTime(time);
        } else if (databaseSelect.equals("pg")) {
            pgFlowMapper.createTable(tableName);
            List<Flow> flowList = pgFlowMapper.selectAllDataByTime(time);
            if (flowList.size() > 0) pgFlowMapper.insertValueByTableName(flowList, tableName);
            pgFlowMapper.deleteDataByTime(time);
        }
    }
}
