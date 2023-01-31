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

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public void addMember(MemberRequest memberRequest) {
        Member member = new Member();
        member.setUserId("dsdfs");
        member.setEmail("test@test.com");
        member.setFirstName("Chinthaka");
        //member.setDateOfBirth(new Date());

        isMemberExist(memberRequest);

        memberRepository.saveAndFlush(member);
    }

    private void isMemberExist(MemberRequest memberRequest) {
        List<Member> memberList = memberRepository.findByEmail(memberRequest.getEmail());

        if (!memberList.isEmpty()) {
            throw new LBMSException(LBMSError.MEMBER_EXISTS);
        }
    }
}
