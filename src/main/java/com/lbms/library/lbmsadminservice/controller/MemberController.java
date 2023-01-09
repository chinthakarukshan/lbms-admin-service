package com.lbms.library.lbmsadminservice.controller;

import com.lbms.library.core.dto.Member;
import com.lbms.library.lbmsadminservice.dto.MemberRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @GetMapping
    public List<Member> getMembers() {
        return new ArrayList<>();
    }

    @PostMapping
    public void addMember(@RequestBody MemberRequest memberRequest) {

    }
}
