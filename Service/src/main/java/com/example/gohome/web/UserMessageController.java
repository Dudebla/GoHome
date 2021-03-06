package com.example.gohome.web;

import com.example.gohome.entity.AreaOrganizer;
import com.example.gohome.entity.MemberMessage;
import com.example.gohome.entity.UserMessage;
import com.example.gohome.service.AreaOrganizerService;
import com.example.gohome.service.MemberMessageService;
import com.example.gohome.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usermessage")
public class UserMessageController {

    @Autowired
    UserMessageService userMessageService;
    @Autowired
    MemberMessageService memberMessageService;
    @Autowired
    AreaOrganizerService areaOrganizerService;

    /**
    * 添加用户信息
    * @return modelMap
    */
    @RequestMapping(value = "/insertusermessage", method = RequestMethod.POST)
    private Map<String, Object> insertUserMessage(@RequestBody UserMessage userMessage){
        System.out.println("insert userMessage: " + userMessage);
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", userMessageService.insertUserMessage(userMessage));
        return modelMap;
    }

    /**
     * 登录获取用户信息
     * @return modelMap
     */
    @GetMapping("loginusermessage")
    public Map<String,Object> loginUserMessage(String userName, String userPassword){
        System.out.println("login username: " + userName + " , password: " + userPassword);
        Map<String, Object> modelMap = new HashMap<>();
        try {
            //由查询登录用户名和密码查询用户信息
            UserMessage userMessage = userMessageService.userMessageByUserPassword(userName, userPassword);
            MemberMessage memberMessage;
            AreaOrganizer areaOrganizer;
            if (userMessage != null) {
                modelMap.put("success", true);
                modelMap.put("userMessage", userMessage);
                switch (userMessage.getUserType()) {
                    case UserMessage.USER_TYPE_NORMAL://普通用户，获取基本信息
                        modelMap.put("type", 0);
                        break;
                    case UserMessage.USER_TYPE_MEMBER://组织成员，获取基本信息和组织信息
                        memberMessage = memberMessageService.memberMessageByUserId(userMessage.getUserId());
                        System.out.println("memberMessage: " + memberMessage.getAreaId());
                        areaOrganizer = areaOrganizerService.areaOrganizerByAreaId(memberMessage.getAreaId());
                        modelMap.put("type", 1);
                        modelMap.put("memberMessage", memberMessage);
                        modelMap.put("areaOrganizer", areaOrganizer);
                        break;
                    case UserMessage.USER_TYPE_ORGANIZER://组织管理员，获取基本信息和组织信息
                        areaOrganizer = areaOrganizerService.areaOrganizerByUserId(userMessage.getUserId());
                        modelMap.put("type", 2);
                        modelMap.put("areaOrganizer", areaOrganizer);
                        break;
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "登录失败: 用户名或密码错误！");
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

}
