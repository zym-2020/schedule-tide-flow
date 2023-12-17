package nnu.edu.schedule.dao.pg;

import nnu.edu.schedule.pojo.Flow;
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
public interface PgFlowMapper {
    void insertValue(@Param("flowList") List<Flow> flowList);

    void createTable(@Param("tableName") String tableName);

    List<Flow> selectAllDataByTime(@Param("time") String time);

    void insertValueByTableName(@Param("flowList") List<Flow> flowList, @Param("tableName") String tableName);

    void deleteDataByTime(@Param("time") String time);
}
