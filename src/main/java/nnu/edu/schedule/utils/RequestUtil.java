package nnu.edu.schedule.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/12/12/21:51
 * @Description:
 */
public class RequestUtil {

    public static <T>T postUtil(String url, HttpHeaders headers, String jsonBody, Class<T> c) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity(jsonBody, headers);
        ResponseEntity<T> result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, c);
        return result.getBody();
    }
}
