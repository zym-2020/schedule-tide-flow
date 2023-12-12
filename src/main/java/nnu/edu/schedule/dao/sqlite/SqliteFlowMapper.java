package nnu.edu.schedule.dao.sqlite;

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
public interface SqliteFlowMapper {
    void insertValue(@Param("flowList") List<Flow> flowList);
}
