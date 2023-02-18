package com.lbms.library.lbmsadminservice.controller;

import com.lbms.library.core.dto.Member;
import com.lbms.library.lbmsadminservice.dto.MemberDTO;
import com.lbms.library.lbmsadminservice.dto.MemberRequest;
import com.lbms.library.lbmsadminservice.dto.MemberSummaryDTO;
import com.lbms.library.lbmsadminservice.dto.MemberUpdateRequest;
import com.lbms.library.lbmsadminservice.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping
    public List<MemberSummaryDTO> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/{userId}")
    public MemberDTO getMemberByID(@PathVariable String userId) {
        return memberService.getMemberbyUserId(userId);
    }

    @PostMapping
    public ResponseEntity addMember(@Valid @RequestBody MemberRequest memberRequest) {
        memberService.addMember(memberRequest);
        return ResponseEntity.created(null).build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity updateMember(@PathVariable String userId, @Valid @RequestBody MemberUpdateRequest memberUpdateRequest) {
        memberService.updateMember(userId,memberUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
