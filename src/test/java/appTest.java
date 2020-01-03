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
    public void ex() throws IOException {

        RequestParams params = new RequestParams();
        params.put("token", "23ee00e3-d09c-4491-af3e-736cace5c045");
        params.put("cardId", 4);
        params.put("expense", 3);
        params.put("phone", "15752400657");
        params.put("password", Utils.toMD5("123456"));
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/v3/userCard/expense", params);
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


    @Test
    public void list() throws IOException {

        RequestParams params = new RequestParams();
        params.put("token", "4e506c4f-5a3d-4b8f-bb34-da61bf39c15a");
        params.put("phone", "15752400657");
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/v3/userCard/user_card", params);
        System.out.println(response.body().string());
    }

    @Test
    public void user_card_detail() throws IOException {

        RequestParams params = new RequestParams();
        params.put("token", "c7f11710-8c13-4cd4-a572-93687dd58f8c");
        params.put("userId", 7);
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/v3/userCard/user_card_detail", params);
        System.out.println(response.body().string());
    }
    @Test
    public void card_status() throws IOException {

        RequestParams params = new RequestParams();
        params.put("token", "4e506c4f-5a3d-4b8f-bb34-da61bf39c15a");
        params.put("storeMemberEventId", 37);
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/v3/userCard/card_status", params);
        System.out.println(response.body().string());
    }

    @Test
    public void refund() throws IOException {

        RequestParams params = new RequestParams();
        params.put("token", "7acc8c34-02e9-4ca7-99ed-69d351006585");
//        params.put("orderNo",71119121421138L);
//        params.put("phone", "18510454067");
//        params.put("code", "18510454067");
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/v3/turnover/record_list", params);
        System.out.println(response.body().string());
    }

    @Test
    public void code() throws IOException {

        RequestParams params = new RequestParams();
//        params.put("token", "01bc341e-94c4-46db-b2ad-ee7b85c3905e");
        params.put("phone", "15752400657");
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/ask/code", params);
        System.out.println(response.body().string());
    }






}
