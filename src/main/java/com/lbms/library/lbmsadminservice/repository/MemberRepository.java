package com.lbms.library.lbmsadminservice.repository;

import com.lbms.library.lbmsadminservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Integer> {
}
