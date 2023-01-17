package com.lbms.library.lbmsadminservice.controller;

import com.lbms.library.core.dto.Member;
import com.lbms.library.lbmsadminservice.dto.MemberRequest;
import com.lbms.library.lbmsadminservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping
    public List<Member> getMembers() {
        return new ArrayList<>();
    }

    @PostMapping
    public ResponseEntity<Object> addMember(@Valid @RequestBody MemberRequest memberRequest) {
        memberService.addMember(memberRequest);
        return ResponseEntity.created(null).build();
    }
}
