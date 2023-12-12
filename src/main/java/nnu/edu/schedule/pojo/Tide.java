package nnu.edu.schedule.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/12/21:13
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tide {
    String stationId;
    String time;
    Float tideValue;
}
