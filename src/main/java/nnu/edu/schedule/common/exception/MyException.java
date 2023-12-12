package nnu.edu.schedule.common.exception;

import lombok.Data;
import nnu.edu.schedule.common.result.ResultEnum;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/12/20:40
 * @Description:
 */
@Data
public class MyException extends RuntimeException{
    Integer code;
    String msg;

    public MyException(ResultEnum resultEnum) {
        super();
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public MyException(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
}
