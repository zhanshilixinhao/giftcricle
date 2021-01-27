package com.chouchong.delayed;
import	java.util.Date;

import com.chouchong.dao.v3.ElUserCouponMapper;
import com.chouchong.dao.v4.PrivilegeCouponsMapper;
import com.chouchong.dao.v4.UserMapper;
import com.chouchong.entity.v3.ElUserCoupon;
import com.chouchong.entity.v4.PrivilegeCoupons;
import com.chouchong.entity.v4.User;
import com.chouchong.utils.sms.SentUtil2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * @Description: 定时发送优惠券提前3 2 1 天到期时间短信
 * @Author Lxh
 * @Date 2020/9/24 13:45
 */
@Component
public class DelayedSmsSend {

    @Resource
    private ElUserCouponMapper elUserCouponMapper;

    @Resource
    private PrivilegeCouponsMapper privilegeCouponsMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * @Description:
     * @Author: LxH
     * @Date: 2020/9/24 15:39
     */
    @Scheduled(cron = "0 0 12 * * ?")
    //@Scheduled(cron="0/3 * *  * * ? ")
    public void sendSms(){
        System.out.println("开始执行当天优惠券到期提醒短信"+ Calendar.getInstance().getTime().toString());
        //查询全部用户优惠券列表
        Example example = new Example(ElUserCoupon.class);
        example.createCriteria().andEqualTo(ElUserCoupon.ELUSERCOUPON_STATUS, 1);
        List<ElUserCoupon> elUserCoupons = elUserCouponMapper.selectByExample(example);
        //获取每条用户优惠券类型
        for (ElUserCoupon elUserCoupon : elUserCoupons) {
            //计算用户优惠券到期时间
            Date reEndTime ;
            PrivilegeCoupons privilegeCoupons = privilegeCouponsMapper.selectByPrimaryKey(elUserCoupon.getCouponId());
            if (privilegeCoupons == null) {
                continue;
            }
            if (privilegeCoupons.getDay() !=null) {
                Calendar calendar = getDate(Calendar.DATE, privilegeCoupons.getDay(), elUserCoupon.getCreated());
                Date dayEndTime = calendar.getTime();
                reEndTime = dayEndTime;
            }else {
                reEndTime = privilegeCoupons.getDate();
            }
            //提前三天到期时间
            Calendar threeDate = getDate(Calendar.DATE, -3, null);
            //提前两天到期时间
            Calendar twoDate = getDate(Calendar.DATE, -2, null);
            //提前一天到期时间
            Calendar oneDate = getDate(Calendar.DATE, -1, null);
            String threeDay = getDay(threeDate);
            String twoDay = getDay(twoDate);
            String oneDay = getDay(oneDate);
            Calendar calen = Calendar.getInstance();
            calen.setTime(reEndTime);
            String reDay = getDay(calen);
            //获取用户电话号码，用于发送提醒短信
            User user = userMapper.selectByPrimaryKey(elUserCoupon.getUserId());
            String userPhone ="";
            if (user !=null) {
               userPhone = user.getPhone();
                //如果相同，发送短信
                if (threeDay.equals(reDay)) {
                    SentUtil2.testSendSms(userPhone,"【礼遇圈】尊敬的"+user.getNickname()+"您的"+privilegeCoupons.getTitle()
                            +3+"天后即将过期!建议您在截止日期之前使用哦,礼遇圈期待您再次光临~");
                }else if (twoDay.equals(reDay)) {
                    SentUtil2.testSendSms(userPhone,"【礼遇圈】尊敬的"+user.getNickname()+"您的"+privilegeCoupons.getTitle()
                            +2+"天后即将过期!建议您在截止日期之前使用哦,礼遇圈期待您再次光临~");
                }else if (oneDay.equals(reDay)) {
                    SentUtil2.testSendSms(userPhone,"【礼遇圈】尊敬的"+user.getNickname()+"您的"+privilegeCoupons.getTitle()
                            +1+"天后即将过期!建议您在截止日期之前使用哦,礼遇圈期待您再次光临~");
                }
            }
        }
        System.out.println("当天优惠券到期提醒短信执行结束=============");
    }


    /**
     * @Description:
     * @Author: LxH
     * @Date: 2020/9/24 15:39
     */
    public static Calendar getDate(Integer date,Integer day,Date time){
        Calendar calendar = Calendar.getInstance();
        if (time!=null) {
            calendar.setTime(time);
        }
        calendar.add(date, day);
        return calendar;
    }

    /**
     * @Description:
     * @Author: LxH
     * @Date: 2020/9/24 15:39
     */
    public String getDay(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        return ""+year+month+day;
    }
}
