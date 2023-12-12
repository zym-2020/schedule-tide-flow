package nnu.edu.schedule.dao.pg;

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
}
