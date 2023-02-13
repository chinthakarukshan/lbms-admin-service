package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.core.exception.LBMSException;
import com.lbms.library.lbmsadminservice.dto.MemberRequest;
import com.lbms.library.lbmsadminservice.entity.Member;
import com.lbms.library.lbmsadminservice.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member;

    private MemberRequest memberRequest;

    @BeforeEach
    public void setup() {
        member = new Member();
        member.setUserId("USER-01");
        member.setEmail("member01@library.com");
        member.setFirstName("Member First Name");
        member.setLastName("Member Last Name");

        memberRequest = new MemberRequest();
        memberRequest.setDateOfBirth(new Date());
        memberRequest.setEmail("member01@library.com");
        memberRequest.setFirstName("Member First Name");
        memberRequest.setLastName("Member Last Name");
    }

    @Test
    public void addMemberTest_Success() {
        List<Member> memberList = new ArrayList<>();
        when(memberRepository.findByEmail("member01@library.com")).thenReturn(memberList);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        memberService.addMember(memberRequest);
        verify(memberRepository,times(1)).save(any(Member.class));
    }

    @Test
    public void addMemberTest_memberAlreadyExist() {
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);

        when(memberRepository.findByEmail("member01@library.com")).thenReturn(memberList);

        assertThrows(LBMSException.class, () -> {memberService.addMember(memberRequest);});

        verify(memberRepository,times(0)).save(any(Member.class));
    }
}
