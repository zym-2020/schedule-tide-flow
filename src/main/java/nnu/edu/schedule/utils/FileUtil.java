package nnu.edu.schedule.utils;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import nnu.edu.schedule.common.exception.MyException;
import nnu.edu.schedule.common.result.ResultEnum;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/17/12:43
 * @Description:
 */
@Slf4j
public class FileUtil {
    public static JSONObject readJson(String path) {
        File file = new File(path);
        if (!file.exists()) {
            log.error("no object");
            return null;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String jsonString = "";
            String line = "";
            while ((line = br.readLine()) != null) {
                jsonString += line;
            }
            br.close();
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            return jsonObject;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                return null;
            }
        }
    }

    public static void writeJson(String path, JSONObject jsonObject) {
        String content = jsonObject.toJSONString();
        try (FileWriter writer = new FileWriter(path)) {
            // 写入内容到文件
            writer.write(content);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
