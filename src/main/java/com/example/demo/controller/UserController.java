package com.example.demo.controller;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.User;
import com.example.demo.service.IUserService;
import com.example.demo.until.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private IUserService userService;
    @GetMapping("/User")
    public List<User> getUser(){
        return userService.getUser();
    }
    @PostMapping("/addUser")
    public int addUser(@RequestBody User user){
        return userService.addUser(user);
    }
    @PostMapping("/user/login")
    public Map<String,Object> login(@RequestBody User user){
        Map<String,Object> map = new HashMap<>();

        try {
            User userDB = userService.login(user);
            Map<String, String> payload = new HashMap<>();
            payload.put("id",userDB.getId());
            payload.put("name",userDB.getUserName());
            // 生成jwt令牌
            String token = JWTUtils.getToken(payload);
            map.put("state",true);
            map.put("msg","认证成功！");
            map.put("token",token);
        } catch (Exception e) {
            map.put("state",false);
            map.put("msg","登录失败");
            map.put("token",null);
        }
        return map;
    }
    @PostMapping("/user/test")
    public Map<String,Object> test(@RequestBody String token){
        System.out.println(token);
        Map<String,Object> map = new HashMap<>();
        try {
            // 验证令牌
            DecodedJWT verify = JWTUtils.verify(token);
            System.out.println(verify.getToken());
            map.put("state",true);
            map.put("msg","请求成功");
            return map;
        } catch (SignatureVerificationException e) {

            map.put("msg","无效签名！");
        }catch (TokenExpiredException e){
            map.put("msg","token过期");
        }catch (AlgorithmMismatchException e){
            map.put("msg","算法不一致");
        }catch (Exception e){
            map.put("msg","token无效！");
        }
        map.put("state",false);
        return map;
    }

}
