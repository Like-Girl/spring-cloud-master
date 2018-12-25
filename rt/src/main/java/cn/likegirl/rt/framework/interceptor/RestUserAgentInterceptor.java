package cn.likegirl.rt.framework.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 模块:【RestTemplate请求拦截器】
 * <p>
 * 开发: Bruce.Liu By 2017/11/20 下午8:06 Create
 */
public class RestUserAgentInterceptor implements ClientHttpRequestInterceptor {
    /**
     * RestTemplate 请求加上默认请求头（签名、时间轴）
     * @param request
     * @param body
     * @param execution
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add("X-UserID","245");
        return execution.execute(request, body);
    }
}