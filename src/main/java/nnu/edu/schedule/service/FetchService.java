package nnu.edu.schedule.service;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/12/21:40
 * @Description:
 */
public interface FetchService {
    void fetchTide(String url, HttpHeaders headers, String jsonBody);

    void fetchFlow(String url, HttpHeaders headers, String jsonBody);
}
