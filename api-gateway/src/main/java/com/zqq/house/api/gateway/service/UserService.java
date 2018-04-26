package com.zqq.house.api.gateway.service;

import com.google.common.collect.Lists;
import com.zqq.house.api.gateway.dao.UserDao;
import com.zqq.house.api.gateway.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/14
 * TIME: 15:45
 */
@Service
public class UserService {


    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserDao userDao;


    public boolean register(User user) {

        //密码加密
        String pwd = DigestUtils.md5DigestAsHex(user.getPasswd().getBytes());
        user.setPasswd(pwd);
        user.setEnable(0);
        user.setCreateTime(new Date());
        //存储头像图片
        List<String> imgPath = fileService.getImgPath(Lists.newArrayList(user.getAvatarFile()));
        if (imgPath != null && !imgPath.isEmpty() && imgPath.size()!=0){
            //图片存储成功 将图片信息存储到user对象中
            user.setAvatar(imgPath.get(0));
        }
        mailService.registerNotify(user.getEmail());
        return userDao.doRegister(user);
    }

    public Boolean enable(String key) {
        return mailService.enable(key);
    }

    public User doLogin(String email, String password) {

        return userDao.doLogin(email,password);
    }

    public Boolean isExist(String email) {
        return false;
    }

    public void logout(String token) {
        userDao.logout(token);
    }

    public Boolean update(User user) {
        if (StringUtils.isNotBlank(user.getPasswd())){
            user.setPasswd(DigestUtils.md5DigestAsHex(user.getPasswd().getBytes()));
        }
        return userDao.update(user);
    }

    /**
     * 根据email 查询用户
     * @return
     */
    public User queryByEmail(String email){
        User user = new User();
        user.setEmail(email);
        return userDao.queryByWhere(user);
    }
}
