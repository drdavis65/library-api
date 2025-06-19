package com.dalto.library_api.dto;

import java.util.List;

public class MemberDTO {    
    private Long id;
    private String fullName;
    private String email;
    private List<String> borrowedBookTitles;

    public MemberDTO() {}

    public MemberDTO(Long id, String fullName, String email, List<String> borrowedBookTitles) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.borrowedBookTitles = borrowedBookTitles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setName(String name) {
        this.fullName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
