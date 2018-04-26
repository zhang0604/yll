package com.zqq.house.api.gateway.apiController;

import com.zqq.house.api.gateway.bean.NewUser;
import com.zqq.house.api.gateway.service.AgencyService;
import com.zqq.house.api.gateway.service.UserService;
import com.zqq.house.api.gateway.commom.CommonProperties;
import com.zqq.house.api.gateway.entity.Agency;
import com.zqq.house.api.gateway.entity.User;
import com.zqq.house.api.gateway.utils.CookieUtils;
import com.zqq.house.api.gateway.utils.UserHelper;
import com.zqq.house.api.gateway.utils.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.zqq.house.api.gateway.bean.ResultMsg;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 16:44
 */
@Controller
@RequestMapping("accounts")
public class ApiUserController {




    @Autowired
    private UserService userService;

    @Autowired
    private AgencyService agencyService;

    @Value("${spring.image.url}")
    private String imageUrl;


    //-------------------------用户注册---------------------------------
    /**
     * 加载注册页面
     * @return
     */
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String register(ModelMap modelMap){

        List<Agency> agencyList = agencyService.selectAgency();
        modelMap.put("agencyList",agencyList);
        return "user/accounts/register";
    }

    /**
     * 执行用户注册
     * @param user
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public String doRegister(User user,ModelMap modelMap){

        //判断 邮箱是否已经被注册
        Boolean boo = userService.isExist(user.getEmail());
        if (boo){
            //验证不通过
            return "redirect:user/accounts/register?" + ResultMsg.ERROR_MSG_KEY +"=" +ResultMsg.error("邮箱已被注册").asUrlParams();
        }
        ResultMsg validate = UserHelper.valiate(user);
        if (validate.isSuccess() && userService.register(user)){
            //验证通过
            modelMap.put("email",user.getEmail());
            return "user/accounts/registerSubmit";
        }else {
            //验证不通过
            return "redirect:user/accounts/register?"+validate.asUrlParams();
        }
    }

    @RequestMapping(value = "verify",method = RequestMethod.GET)
    public String doVerify(@RequestParam("key")String key){

        Boolean flag = userService.enable(key);
        if (flag) {
            return "redirect:/index";
        }else {
            return "redirect:user/accounts/register";
        }
    }


    /*******执行登陆相关操作*********************************************/
    @RequestMapping(value = "signin",method = RequestMethod.GET)
    public String signin(){
        return "user/accounts/signin";
    }


    /**
     * 用户登录
     * @param request
     * @param email
     * @param password
     * @param target
     * @return
     */
    @RequestMapping(value = "signin",method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, @RequestParam("username")String email,
                        @RequestParam("password")String password,
                        @RequestParam(value = "target",required = false)String target){
        //判断用户名密码是否为空
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)){
            return "/user/accounts/signin";
        }
        //调用 UserService的登陆方法进行登陆造作
        User user = userService.doLogin(email, password);
        if (null == user){
            //登陆失败,重定向
            return "redirect:/accounts/signin?" + "username=" + email + "&" + ResultMsg.error("用户名或密码错误").asUrlParams();
        }
        //将用户对象 放入本地线程中
        UserThreadLocal.setUser(user);
        CookieUtils.setCookie(response,CommonProperties.USER_TOKEN,user.getToken(),1800);
        //判断 请求是否来自 拦截器
        Cookie cookie = WebUtils.getCookie(request, CommonProperties.REFERER_URL);
        if (null != cookie && StringUtils.isNotBlank(cookie.getValue())){
            String referer = cookie.getValue();
            //定向到 拦截前的页面
            return "redirect:"+referer;
        }
        request.getSession().setAttribute(CommonProperties.LOGIN_USER_MSG,user);
        return "redirect:/index";
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        request.getSession().invalidate();
        User user = UserThreadLocal.getUser();
        userService.logout(user.getToken());
        //删除 cookie信息
        CookieUtils.deleteCookie(response,CommonProperties.USER_TOKEN);
        return "redirect:/index";
    }

    /**
     * 个人信息页
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "profile",method = RequestMethod.GET)
    public String profile(ModelMap modelMap,HttpServletRequest request){
        User user = UserThreadLocal.getUser();
        user.setAvatar(imageUrl+user.getAvatar());
        //request.getSession().setAttribute(CommonProperties.LOGIN_USER_MSG,user);
        return "user/accounts/profile";
    }

    /**
     * 更新用户信息
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "profile",method = RequestMethod.POST)
    public String updateProfile(User user,HttpServletRequest request){
        Boolean update = userService.update(user);
        if (update){
            User userInfo = userService.queryByEmail(user.getEmail());
            request.getSession().setAttribute(CommonProperties.LOGIN_USER_MSG,userInfo);
            return "redirect:/accounts/profile?" + ResultMsg.succss("更新成功").asUrlParams();
        }
        throw new RuntimeException("更新失败");

    }

    /**
     * 更新密码
     * @param newUser
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "changePassword",method = RequestMethod.POST)
    public String changePassword(NewUser newUser,ModelMap modelMap,HttpServletRequest request){

        if (StringUtils.isEmpty(newUser.getEmail()) || StringUtils.isEmpty(newUser.getPassword())){
            return "redirect:/accounts/profile?" + ResultMsg.error("密码不能为空").asUrlParams();
        }

        User user = userService.doLogin(newUser.getEmail(), newUser.getPassword());
        if (null == user){
            return "redirect:/accounts/profile?" + ResultMsg.error("密码错误").asUrlParams();
        }

        user.setPasswd(newUser.getNewPassword());
        userService.update(user);
        request.getSession().setAttribute(CommonProperties.LOGIN_USER_MSG,user);
        return "redirect:/accounts/profile?" + ResultMsg.succss("更新成功").asUrlParams();
    }
}
