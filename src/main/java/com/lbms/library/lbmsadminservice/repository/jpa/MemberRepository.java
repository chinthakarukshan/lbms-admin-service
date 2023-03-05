package com.lbms.library.lbmsadminservice.repository.jpa;

import com.lbms.library.lbmsadminservice.entity.jdbc.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Integer> {

    List<Member> findByEmail(String email);

    List<Member> findByUserId(String userId);
}
