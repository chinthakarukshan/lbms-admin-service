package com.lbms.library.lbmsadminservice.service.impl;

import com.lbms.library.core.error.LBMSError;
import com.lbms.library.core.exception.LBMSException;
import com.lbms.library.lbmsadminservice.dto.MemberDTO;
import com.lbms.library.lbmsadminservice.dto.MemberRequest;
import com.lbms.library.lbmsadminservice.dto.MemberSummaryDTO;
import com.lbms.library.lbmsadminservice.dto.MemberUpdateRequest;
import com.lbms.library.lbmsadminservice.entity.jdbc.Member;
import com.lbms.library.lbmsadminservice.repository.jpa.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member;
    private Member member2;
    private Member member3;

    private MemberRequest memberRequest;

    private MemberUpdateRequest memberUpdateRequest;

    @BeforeEach
    public void setup() {
        member = new Member();
        member.setUserId("USER-01");
        member.setEmail("member01@library.com");
        member.setFirstName("Member First Name");
        member.setLastName("Member Last Name");

        member2 = new Member();
        member2.setUserId("USER-02");
        member2.setEmail("member02@library.com");
        member2.setFirstName("Member2 First Name");
        member2.setLastName("Member2 Last Name");

        member3 = new Member();
        member3.setUserId("USER-03");
        member3.setEmail("member03@library.com");
        member3.setFirstName("Member3 First Name");
        member3.setLastName("Member3 Last Name");

        memberRequest = new MemberRequest();
        memberRequest.setDateOfBirth(new Date());
        memberRequest.setEmail("member01@library.com");
        memberRequest.setFirstName("Member First Name");
        memberRequest.setLastName("Member Last Name");

        memberUpdateRequest = new MemberUpdateRequest();
        memberUpdateRequest.setEmail("member01@library.com");
        memberUpdateRequest.setFirstName("first Name");
        memberUpdateRequest.setLastName("last name");
        memberUpdateRequest.setDateOfBirth(new Date());
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

    @Test
    public void getMemberbyUserId_Success() {
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);
        when(memberRepository.findByUserId(any(String.class))).thenReturn(memberList);

        MemberDTO memberDTO = memberService.getMemberbyUserId("USER-01");

        assert(memberDTO.getUserId().equals(member.getUserId()));

    }

    @Test
    public void getMemberbyUserId_userNotExist() {
        List<Member> memberList = new ArrayList<>();
        when(memberRepository.findByUserId(any(String.class))).thenReturn(memberList);

        LBMSException lbmsException = assertThrows(LBMSException.class, () -> {
            memberService.getMemberbyUserId("USER-01");
        });

        assert(lbmsException.getThrowableError().equals(LBMSError.MEMBER_DOES_NOT_EXIST));
    }

    @Test
    public void getMembers_success() {
        ModelMapper mapper = new ModelMapper();
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);
        memberList.add(member2);
        memberList.add(member3);

        List<MemberSummaryDTO> expectedMemberSummaryDTOList = memberList.stream().map(member -> mapper.map(member,MemberSummaryDTO.class)).collect(Collectors.toList());

        when(memberRepository.findAll()).thenReturn(memberList);

        List<MemberSummaryDTO> returnedMmemberSummaryDTOList = memberService.getMembers();

        assert(expectedMemberSummaryDTOList.size() == returnedMmemberSummaryDTOList.size());
        assert(expectedMemberSummaryDTOList.get(0).getUserId().equals(returnedMmemberSummaryDTOList.get(0).getUserId()));

    }

    @Test
    public void updateMember_success() {
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);
        when(memberRepository.findByUserId("USER-01")).thenReturn(memberList);

        memberService.updateMember("USER-01", memberUpdateRequest);

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void updateMember_memberNotExist() {
        List<Member> memberList = new ArrayList<>();

        when(memberRepository.findByUserId("USER-02")).thenReturn(memberList);

        LBMSException lbmsException = assertThrows(LBMSException.class, () -> {
            memberService.updateMember("USER-02", memberUpdateRequest);
        });

        assert (lbmsException.getThrowableError()
                             .equals(LBMSError.INVALID_MEMBER_TO_UPDATE));
    }

    @Test
    public void updateMember_emailChangeAttempt() {
        List<Member> memberList = new ArrayList<>();
        member.setEmail("updated@library.com");
        memberList.add(member);

        when(memberRepository.findByUserId("USER-02")).thenReturn(memberList);

        LBMSException lbmsException = assertThrows(LBMSException.class, () -> {
            memberService.updateMember("USER-02", memberUpdateRequest);
        });

        assert (lbmsException.getThrowableError()
                             .equals(LBMSError.EMAIL_CANNOT_BE_UPDATED));
    }
}
