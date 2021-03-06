package com.example.gohome.web;


import com.example.gohome.entity.MemberMessage;
import com.example.gohome.entity.ResponseEntity.ResponseMemberMessage;
import com.example.gohome.service.MemberMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/membermessage")
public class MemberMessageController {

    @Autowired
    MemberMessageService memberMessageService;

    /**
     * 获取组织所有成员
     * @return modelMap
     */
    @GetMapping("membermessageofarea")
    public Map<String, Object> memberMessageOfArea(Integer areaId){
        System.out.println("areaId: " + areaId);
        Map<String, Object> modelMap = new HashMap<>();
        try{
            List<ResponseMemberMessage> responseMemberMessage = memberMessageService.queryMemberMessageByAreaId(areaId);
            if(responseMemberMessage==null){
                throw new RuntimeException("查无组员");
            }
            modelMap.put("memberMessage", responseMemberMessage);
            modelMap.put("success", true);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        return modelMap;
    }
}
