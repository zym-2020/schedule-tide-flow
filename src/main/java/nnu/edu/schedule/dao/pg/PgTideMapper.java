package nnu.edu.schedule.dao.pg;

import nnu.edu.schedule.pojo.Flow;
import nnu.edu.schedule.pojo.Tide;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/12/20:57
 * @Description:
 */
@Repository
public interface PgTideMapper {
    void insertValue(@Param("tideList") List<Tide> tideList);

    void createTable(@Param("tableName") String tableName);

    List<Tide> selectAllDataByTime(@Param("time") String time);

    List<Tide> selectDataByStationIdAndTime(@Param("stationId") String stationId, @Param("time") String time);

    List<Tide> selectDataByStationIdAndStartTimeAndEndTime(@Param("stationId") String stationId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Tide> selectDataByStartTimeAndEndTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    void insertValueByTableName(@Param("tideList") List<Tide> tideList, @Param("tableName") String tableName);

    void deleteDataByTime(@Param("time") String time);
}
