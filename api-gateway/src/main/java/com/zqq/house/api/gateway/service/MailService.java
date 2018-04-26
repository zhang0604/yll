package com.zqq.house.api.gateway.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.zqq.house.api.gateway.dao.UserDao;
import com.zqq.house.api.gateway.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created By 张庆庆
 * DATA: 2018/4/14
 * TIME: 10:11
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserDao userDao;

    @Value("${properties.myHost}")
    private String myHost; //本地域名

    @Value("${spring.mail.username}")
    private String from;

    Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES).removalListener(new RemovalListener<String, String>() {

        @Override
        public void onRemoval(RemovalNotification<String, String> removalNotification) {
            //超时 操作-->删除数据库存储的 未激活的email
            //TODO
            userDao.delete(removalNotification.getValue());
        }
    }).build();


    /**
     * 发送邮件的准备工作
     * @param mail
     */
    @Async
    public void registerNotify(String mail){
        //生成 邮件对应的key
        String key = RandomStringUtils.randomAlphabetic(10);
        //将key和email放入本地缓存中
        cache.put(key,mail);
        //构造 激活邮件地址
        String url = myHost + "/accounts/verify?key=" + key;
        sendMail("房产激活邮件:",url,mail);

    }

    /**
     * 执行发送邮件操作
     * @param title
     * @param url
     * @param mail
     */
    public void sendMail(String title, String url, String mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(mail);
        mailMessage.setText(url);
        mailMessage.setSubject(title);
        mailSender.send(mailMessage);
    }


    /**
     * 激活 账号
     * @param key
     * @return
     */
    public Boolean enable(String key) {

        String email = cache.getIfPresent(key);
        if (StringUtils.isEmpty(email)){
            return false;
        }
        //更改 激活状态
        User user = new User();
        user.setEmail(email);
        user.setEnable(1);

        //通过userDao调用接口服务 激活用户
        return userDao.update(user);
    }
}
