import com.chouchong.common.Constants;
import com.chouchong.common.Utils;
import com.chouchong.service.order.kdapi.OkHttpManager;
import com.chouchong.service.order.kdapi.OkHttpUtil;
import com.chouchong.service.order.kdapi.RequestParams;
import com.chouchong.utils.ApiSignUtil;
import okhttp3.Response;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linqin
 * @date 2018/12/10 14:36
 */

public class appTest {

    @Test
    public void login() throws IOException {

        RequestParams params = new RequestParams();
        params.put("username", "admin");
        params.put("password", "123456");
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/user/add", params);
        System.out.println(response.body().string());
    }

    @Test
    public void info() throws IOException {

        RequestParams params = new RequestParams();
        params.put("token", "7171d718-0c30-4fd0-9a6b-53a865be71a5");
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/user/info", params);
        System.out.println(response.body().string());
    }

    @Test
    public void store() throws IOException {

        RequestParams params = new RequestParams();
        params.put("token", "460eec83-8cfc-480c-b709-41591bffb81c");
        params.put("membershipCardId", 4);
        params.put("phone", "15752400657");
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/v3/userCard/add", params);
        System.out.println(response.body().string());
    }
    @Test
    public void storee() throws IOException {

        RequestParams params = new RequestParams();
        params.put("token", "460eec83-8cfc-480c-b709-41591bffb81c");
        params.put("type", 1);
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/v3/card/store_card", params);
        System.out.println(response.body().string());
    }

    @Test
    public void all_event() throws IOException {

        RequestParams params = new RequestParams();
        params.put("token", "460eec83-8cfc-480c-b709-41591bffb81c");
        params.put("cardId", 4);
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/v3/cardEvent/all_event", params);
        System.out.println(response.body().string());
    }

    @Test
    public void charge() throws IOException {

        RequestParams params = new RequestParams();
        params.put("token", "460eec83-8cfc-480c-b709-41591bffb81c");
        params.put("startTime", 1576771200000L);
        params.put("endTime", 1576771200000L);
//        params.put("phone", 200);
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/v3/turnover/refund_list", params);
        System.out.println(response.body().string());
    }






}
