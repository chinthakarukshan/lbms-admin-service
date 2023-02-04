package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.core.error.LBMSError;
import com.lbms.library.core.exception.LBMSException;
import com.lbms.library.lbmsadminservice.dto.MemberDTO;
import com.lbms.library.lbmsadminservice.dto.MemberRequest;
import com.lbms.library.lbmsadminservice.dto.MemberSummaryDTO;
import com.lbms.library.lbmsadminservice.entity.Member;
import com.lbms.library.lbmsadminservice.repository.MemberRepository;
import com.lbms.library.lbmsadminservice.service.MemberService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

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

    @Override
    public List<MemberSummaryDTO> getMembers() {
        ModelMapper mapper = new ModelMapper();
        List<Member> memberList = memberRepository.findAll();
        List<MemberSummaryDTO> memberSummaryList = memberList.stream()
                                                             .map(member -> mapper.map(member, MemberSummaryDTO.class))
                                                             .collect(Collectors.toList());
        return memberSummaryList;
    }

    @Override
    public MemberDTO getMemberbyUserId(String userId) {
        List<Member> memberList = memberRepository.findByUserId(userId);
        ModelMapper mapper = new ModelMapper();

        if (memberList.isEmpty()) {
            log.info("Member with the user id doesn't exist in the system");
            throw new LBMSException(LBMSError.MEMBER_DOES_NOT_EXIST);
        }

        Member member = memberList.get(0);
        MemberDTO memberDTO = mapper.map(member,MemberDTO.class);

        return memberDTO;
    }

    private void isMemberExist(MemberRequest memberRequest) {
        List<Member> memberList = memberRepository.findByEmail(memberRequest.getEmail());

        if (!memberList.isEmpty()) {
            log.info("Member already exists with the email address");
            throw new LBMSException(LBMSError.MEMBER_EXISTS);
        }
    }
}
