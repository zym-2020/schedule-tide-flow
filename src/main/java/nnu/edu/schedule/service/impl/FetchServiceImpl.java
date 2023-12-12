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
import org.springframework.util.MultiValueMap;

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
                tideList.add(new Tide(tide.getString("STCD"), tide.getString("TM"), tide.getFloat("TDZ")));
            }
            if (databaseSelect.equals("sqlite")) sqliteTideMapper.insertValue(tideList);
            else pgTideMapper.insertValue(tideList);
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
                flowList.add(new Flow(flow.getString("STCD"), flow.getString("TM"), flow.getFloat("Z"), flow.getFloat("Q")));
            }
            if (databaseSelect.equals("sqlite")) sqliteFlowMapper.insertValue(flowList);
            else pgFlowMapper.insertValue(flowList);
        } else log.error("tide请求错误");
    }
}
