package com.lbms.library.lbmsadminservice.service;


import com.lbms.library.lbmsadminservice.dto.MemberDTO;
import com.lbms.library.lbmsadminservice.dto.MemberRequest;
import com.lbms.library.lbmsadminservice.dto.MemberSummaryDTO;
import com.lbms.library.lbmsadminservice.dto.MemberUpdateRequest;
import com.lbms.library.lbmsadminservice.entity.Member;

import java.util.List;

public interface MemberService {

    void addMember(MemberRequest memberRequest);

    List<MemberSummaryDTO> getMembers();

    MemberDTO getMemberbyUserId(String userId);

    void updateMember(String userId, MemberUpdateRequest memberUpdateRequest);
}
