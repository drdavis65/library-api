package com.dalto.library_api.service;

import java.util.List;
import java.util.Set;

import com.dalto.library_api.model.Book;
import com.dalto.library_api.model.Member;

public interface IMemberService {
    List<Member> getAllMembers();
    Member getMemberById(Long id);
    Member addMember(Member member);
    Member updateMember(Long id, Member updatedMember);
    void deleteMember(Long id);
    Set<Book> getBooksBorrowedByMember(Long memberId);
    void returnAllBooks(Long memberId); // optional
}

