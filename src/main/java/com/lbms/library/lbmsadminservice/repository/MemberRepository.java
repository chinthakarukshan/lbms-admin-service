package com.lbms.library.lbmsadminservice.repository;

import com.lbms.library.lbmsadminservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Integer> {

    List<Member> findByEmail(String email);

    List<Member> findByUserId(String userId);
}
