package com.example.gohome.service;

import com.example.gohome.entity.MemberMessage;
import com.example.gohome.entity.ResponseEntity.ResponseMemberMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberMessageService {

    boolean insertMemberMessage(MemberMessage memberMessage);

    boolean updateMemberMessage(MemberMessage memberMessage);

    MemberMessage memberMessageByUserId(Integer userId);

    MemberMessage memberMessageByMessageId(Integer memberId);

    /**
     * 未实现！！
     */
    List<ResponseMemberMessage> queryMemberMessageByAreaId(Integer areaId);
}
