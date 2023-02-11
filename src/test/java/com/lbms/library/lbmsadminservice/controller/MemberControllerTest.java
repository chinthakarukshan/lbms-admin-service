package com.lbms.library.lbmsadminservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbms.library.lbmsadminservice.dto.MemberRequest;
import com.lbms.library.lbmsadminservice.dto.MemberSummaryDTO;
import com.lbms.library.lbmsadminservice.service.MemberService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @MockBean
    MemberService memberService;
    @Autowired
    private MockMvc mockMVC;

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
}
