package com.lbms.library.lbmsadminservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbms.library.core.dto.member.MemberDTO;
import com.lbms.library.core.dto.member.MemberSummaryDTO;
import com.lbms.library.core.error.LBMSError;
import com.lbms.library.core.exception.LBMSException;
import com.lbms.library.lbmsadminservice.dto.MemberRequest;
import com.lbms.library.lbmsadminservice.dto.MemberUpdateRequest;
import com.lbms.library.lbmsadminservice.entity.jdbc.Member;
import com.lbms.library.lbmsadminservice.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @MockBean
    MemberService memberService;
    @Autowired
    private MockMvc mockMVC;

    Member member;

    MemberUpdateRequest memberUpdateRequest;

    @BeforeEach
    public void setup() {
        member = new Member();
        member.setId(1);
        member.setUserId("USER-01");
        member.setEmail("test@org.com");
        member.setFirstName("First name");
        member.setLastName("Last name");

        memberUpdateRequest = new MemberUpdateRequest();
        memberUpdateRequest.setEmail("test@org.com");
        memberUpdateRequest.setFirstName("first Name");
        memberUpdateRequest.setLastName("last name");
        memberUpdateRequest.setDateOfBirth(new Date());
    }

    @Test
    public void addMemberTest_success() throws Exception {
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setEmail("testemail@test.com");
        memberRequest.setFirstName("test first");
        memberRequest.setLastName("test Last");
        memberRequest.setDateOfBirth(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(memberRequest);

        this.mockMVC.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
                                             .content(json))
                    .andExpect(status().isCreated());
    }

    @Test
    public void addMemberTest_validationFailure_Email() throws Exception {
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setFirstName("test first");
        memberRequest.setLastName("test Last");
        memberRequest.setDateOfBirth(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(memberRequest);

        this.mockMVC.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
                                             .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Email is Required")));
    }

    @Test
    public void addMemberTest_validationFailure_FirstName() throws Exception {
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setEmail("testemail@test.com");
        memberRequest.setLastName("test Last");
        memberRequest.setDateOfBirth(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(memberRequest);

        this.mockMVC.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
                                             .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("First Name is Required")));
    }

    @Test
    public void addMemberTest_validationFailure_LastName() throws Exception {
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setEmail("testemail@test.com");
        memberRequest.setFirstName("test first");
        memberRequest.setDateOfBirth(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(memberRequest);

        this.mockMVC.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
                                             .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Last Name is Required")));
    }

    @Test
    public void addMemberTest_validationFailure_DOB() throws Exception {
        MemberRequest memberRequest = new MemberRequest();
        memberRequest.setEmail("testemail@test.com");
        memberRequest.setFirstName("test first");
        memberRequest.setLastName("test Last");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(memberRequest);

        this.mockMVC.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
                                             .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Date of Birth is Required")));
    }

    @Test
    public void getMembersTest_success() throws Exception {
        MemberSummaryDTO member01 = new MemberSummaryDTO();
        member01.setEmail("mail01@mailorg.com");
        member01.setFirstName("member 01");
        member01.setLastName("last 01");
        member01.setUserId("UUID-01");

        MemberSummaryDTO member02 = new MemberSummaryDTO();
        member02.setEmail("mail02@mailorg.com");
        member02.setFirstName("member 02");
        member02.setLastName("last 02");
        member02.setUserId("UUID-02");

        MemberSummaryDTO member03 = new MemberSummaryDTO();
        member03.setEmail("mail03@mailorg.com");
        member03.setFirstName("member 03");
        member03.setLastName("last 03");
        member03.setUserId("UUID-03");

        List<MemberSummaryDTO> memberSummaryDTOList = new ArrayList<>();
        memberSummaryDTOList.add(member01);
        memberSummaryDTOList.add(member02);
        memberSummaryDTOList.add(member03);

        when(memberService.getMembers()).thenReturn(memberSummaryDTOList);

        this.mockMVC.perform(get("/members"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().string(containsString("UUID-01")));
    }

    @Test
    public void getMemberById_success() throws Exception {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId("USERID-01");
        memberDTO.setEmail("user01@org.com");
        memberDTO.setFirstName("First Name");
        memberDTO.setLastName("Last Name");

        when(memberService.getMemberbyUserId("USERID-01")).thenReturn(memberDTO);

        this.mockMVC.perform(get("/members/USERID-01"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("user01@org.com")));
    }

    @Test
    public void getMemberById_invalidUserId() throws Exception {
        when(memberService.getMemberbyUserId("USERID-02")).thenThrow(new LBMSException(LBMSError.MEMBER_DOES_NOT_EXIST));

        this.mockMVC.perform(get("/members/USERID-02"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("LBMSVAL0002")))
                    .andExpect(content().string(containsString("A member with the user id does not exist")));
    }

    @Test
    public void updateMember_success() throws Exception {
        MemberUpdateRequest memberUpdateRequestSuccess = new MemberUpdateRequest();
        memberUpdateRequestSuccess.setEmail("test@org.com");
        memberUpdateRequestSuccess.setFirstName("first Name");
        memberUpdateRequestSuccess.setLastName("last name");
        memberUpdateRequestSuccess.setDateOfBirth(new Date());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(memberUpdateRequestSuccess);

        this.mockMVC.perform(patch("/members/USER-01").contentType(MediaType.APPLICATION_JSON)
                                                      .content(json))
                    .andExpect(status().isOk());
    }

    @Test
    public void updateMember_invalidUser() throws Exception {
         doThrow(new LBMSException(LBMSError.INVALID_MEMBER_TO_UPDATE))
               .when(memberService)
               .updateMember(any(), any());


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(memberUpdateRequest);

        this.mockMVC.perform(patch("/members/USER-01").contentType(MediaType.APPLICATION_JSON)
                                                      .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(LBMSError.INVALID_MEMBER_TO_UPDATE.getCode())));
    }

    @Test
    public void updateMember_firstnameEmpty() throws Exception {
        memberUpdateRequest.setFirstName(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(memberUpdateRequest);

        this.mockMVC.perform(patch("/members/USER-01").contentType(MediaType.APPLICATION_JSON)
                                                      .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("First Name is Required")));
    }

    @Test
    public void updateMember_lastnameEmpty() throws Exception {
        memberUpdateRequest.setLastName(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(memberUpdateRequest);

        this.mockMVC.perform(patch("/members/USER-01").contentType(MediaType.APPLICATION_JSON)
                                                      .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Last Name is Required")));
    }

    @Test
    public void updateMember_dobEmpty() throws Exception {
        memberUpdateRequest.setDateOfBirth(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(memberUpdateRequest);

        this.mockMVC.perform(patch("/members/USER-01").contentType(MediaType.APPLICATION_JSON)
                                                      .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Date of Birth is Required")));
    }
}
