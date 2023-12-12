package nnu.edu.schedule.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/12/20:15
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<T> {
    private Integer code;

    private String msg;

    private T data;
}
