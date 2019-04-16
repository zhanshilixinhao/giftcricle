import com.chouchong.common.Constants;
import com.chouchong.common.Utils;
import com.chouchong.service.order.kdapi.OkHttpManager;
import com.chouchong.service.order.kdapi.OkHttpUtil;
import com.chouchong.service.order.kdapi.RequestParams;
import com.chouchong.utils.ApiSignUtil;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
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
//        params.put("exploringId", 24);
//        params.put("time", "1526539545791");
//        params.put("app_id", "giftcircler-dl");
//        params.put("app_secret", "qMEjFl8w63EtAX17cRX83L0iMkK2U4mg");
//        Map map = ApiSignUtil.sign1(params.getParams(), ApiSignUtil.IOS);
//        params.put("sign", map.get(ApiSignUtil.IOS));
//        System.out.println(map);
        Response response = OkHttpUtil.post(OkHttpManager.create(null, null),
                "http://localhost:8080/manage/user/add", params);
        System.out.println(response.body().string());
    }

    @Test
    public void pas() throws IOException {
        String password = Utils.toMD5(Utils.toMD5("123456") + Constants.ADMINPWD);
        System.out.println(password);
    }


}
