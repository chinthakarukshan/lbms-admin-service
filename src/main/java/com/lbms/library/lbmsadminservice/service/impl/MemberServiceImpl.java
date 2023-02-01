package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.core.error.LBMSError;
import com.lbms.library.core.exception.LBMSException;
import com.lbms.library.lbmsadminservice.dto.MemberRequest;
import com.lbms.library.lbmsadminservice.entity.Member;
import com.lbms.library.lbmsadminservice.repository.MemberRepository;
import com.lbms.library.lbmsadminservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public void addMember(MemberRequest memberRequest) {
        isMemberExist(memberRequest);

        UUID uuid = UUID.randomUUID();
        String userId = uuid.toString();

        Member member = new Member();
        member.setEmail(memberRequest.getEmail());
        member.setFirstName(memberRequest.getFirstName());
        member.setLastName(memberRequest.getLastName());
        member.setUserId(userId);

        memberRepository.saveAndFlush(member);
    }

    private void isMemberExist(MemberRequest memberRequest) {
        List<Member> memberList = memberRepository.findByEmail(memberRequest.getEmail());

        if (!memberList.isEmpty()) {
            throw new LBMSException(LBMSError.MEMBER_EXISTS);
        }
    }
}
