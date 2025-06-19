package com.dalto.library_api.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dalto.library_api.dto.MemberDTO;
import com.dalto.library_api.model.Book;
import com.dalto.library_api.model.Member;
import com.dalto.library_api.repository.BookRepository;
import com.dalto.library_api.repository.MemberRepository;

@Service
public class MemberService implements IMemberService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    
    public MemberService(BookRepository bookRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    private MemberDTO toDTO(Member member) {
        String fullName = member.getFirstName() + " " + member.getLastName();

        List<String> bookTitles = member.getBooksBorrowed().stream()
            .map(Book::getTitle)
            .collect(Collectors.toList());

        return new MemberDTO(
            member.getId(),
            fullName,
            member.getEmail(),
            bookTitles
        );
    }

    public List<MemberDTO> getAllMembersAsDTOs() {
        return memberRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public MemberDTO getMemberDTOById(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Member not found"));
        return toDTO(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }

    @Override
    public Member addMember(Member member) {
        if (memberRepository.existsById(member.getId())) {
            throw new RuntimeException("Member already exists with id: " + member.getId());
        }
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long id, Member updatedMember) {
        Member existingMember = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));

        existingMember.setFirstName(updatedMember.getFirstName());
        existingMember.setLastName(updatedMember.getLastName());
        existingMember.setEmail(updatedMember.getEmail());

        return memberRepository.save(existingMember);
    }


    @Override
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }

    @Override
    public Set<Book> getBooksBorrowedByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        return member.getBooksBorrowed();
    }

    @Override
    public void returnAllBooks(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        Set<Book> borrowedBooks = member.getBooksBorrowed();
        for (Book book : borrowedBooks) {
            member.returnBook(book);
            bookRepository.save(book);
        }
        memberRepository.save(member);
    }
    
}
