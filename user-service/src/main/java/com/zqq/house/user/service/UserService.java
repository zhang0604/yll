package com.zqq.house.user.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zqq.house.user.entity.User;
import com.zqq.house.user.enums.ExceptionEnum;
import com.zqq.house.user.mapper.UserMapper;
import com.zqq.house.user.myexception.UserException;
import com.zqq.house.user.utils.JWTHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 22:43
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${spring.image.url}")
    private String imgPrefix;



    /**
     * 用户注册
     * @param user
     * @return
     */
    public boolean register(User user) {
        return userMapper.insert(user) == 1;
    }


    /**
     * 用户登陆
     * @param key
     * @return
     */
    public Boolean enable(String key) {



        return true;
    }

    /**
     * 用户登录
     * 生成token
     * @param email
     * @param passwd
     * @return
     */
    public User doLogin(String email, String passwd) {

        if (StringUtils.isBlank(email) || StringUtils.isBlank(passwd)){
            throw new UserException(ExceptionEnum.ILLEGAL_PARAM);
        }
        User user = new User();
        user.setEmail(email);
        user.setEnable(1);
        user.setPasswd(DigestUtils.md5DigestAsHex(passwd.getBytes()));
        List<User> users = selectUsersByQuery(user);
        if (!users.isEmpty()){
            User userInfo = users.get(0);
            //生成 token 并设置进用户信息
            createToken(userInfo);
            return userInfo;
        }
        throw new UserException(ExceptionEnum.USER_NOT_EXIST);
    }

    public void createToken(User userInfo) {
        Map<String,String> map = Maps.newHashMap();
        map.put("email",userInfo.getEmail());
        map.put("name",userInfo.getName());
        String token = JWTHelper.getToken(map);
        userInfo.setToken(token);

        //将 user信息存入redis缓存中,并设置过期时间
        setRedis(userInfo.getEmail(),userInfo.getToken());
        userInfo.setToken(token);
    }


    /**
     * 对查询到的 用户信息 头像地址进行处理
     * @param user
     * @return
     */
    public List<User> selectUsersByQuery(User user){
        List<User> users = userMapper.selectUsersByQuery(user);
        users.forEach(u->u.setAvatar(imgPrefix + u.getAvatar()));
        return users;
    }


    /**
     * 将用户信息存入redis 并设置过期时间
     * @param email
     * @param token
     */
    public String setRedis(String email,String token){
        redisTemplate.opsForValue().set(email,token);
        redisTemplate.expire(email,30, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 根据用户id 查询用户信息
     * @param id
     * @return
     */
    public User queryUserById(Long id) {
        User user = new User();
        user.setId(id);
        user.setEnable(1);
        List<User> users = selectUsersByQuery(user);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    public User verify(String token){
        Map<String, String> map = JWTHelper.verifyToken(token);
        if (map.isEmpty()){
            //token中没有用户信息
            throw new UserException(ExceptionEnum.NOT_LOGIN);
        }
        String email = map.get("email");
        String userToken = redisTemplate.opsForValue().get(email);
        if (StringUtils.isBlank(userToken)){
            //redis中没有用户Token,登陆超时,或未登录
            throw new UserException(ExceptionEnum.NOT_LOGIN);
        }
        User userInfo = getUserByEmail(email);
        userInfo.setToken(userToken);
        return userInfo;
    }


    public User getUserByEmail(String email){
        User user = new User();
        user.setEmail(email);
        List<User> userList = selectUsersByQuery(user);
        return userList.isEmpty() ? null : userList.get(0);
    }

    public Boolean delete(String token){
        Map<String, String> map = JWTHelper.verifyToken(token);
        if (map.isEmpty()){
            throw new UserException(ExceptionEnum.NOT_LOGIN);
        }
        String email = map.get("email");
        //删除redis中的用户信息
        return redisTemplate.delete(email);
    }


    public Boolean deleteByEmail(String email) {

        return userMapper.delete(email) == 1;
    }

    public Boolean update(User user) {
        return userMapper.update(user) == 1;
    }

    public User getUser(String email, String passwd) {
        User user = new User();
        user.setEmail(email);
        user.setPasswd(DigestUtils.md5DigestAsHex(passwd.getBytes()));
        List<User> userList = selectUsersByQuery(user);
        return userList.isEmpty()? null: userList.get(0);
    }
}
