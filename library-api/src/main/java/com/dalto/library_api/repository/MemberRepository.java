package com.dalto.library_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dalto.library_api.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    
}
