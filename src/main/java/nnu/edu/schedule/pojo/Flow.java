package nnu.edu.schedule.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/12/21:14
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flow {
    String stationId;
    String time;
    Float waterLevelValue;
    Float flowValue;
}
