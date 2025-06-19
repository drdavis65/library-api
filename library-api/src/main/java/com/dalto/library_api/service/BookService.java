package com.dalto.library_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dalto.library_api.dto.BookDTO;
import com.dalto.library_api.model.Book;
import com.dalto.library_api.model.Member;
import com.dalto.library_api.repository.BookRepository;
import com.dalto.library_api.repository.MemberRepository;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BookService(BookRepository bookRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    private BookDTO toDTO(Book book) {
        String authorNames = book.getAuthors().stream()
            .map(a -> a.getFirstName() + " " + a.getLastName())
            .collect(Collectors.joining(", "));

        String borrower = (book.getBorrowedBy() != null)
            ? book.getBorrowedBy().getFirstName() + " " + book.getBorrowedBy().getLastName()
            : null;

        return new BookDTO(
            book.getId(),
            book.getTitle(),
            authorNames,
            book.getIsbn(),
            borrower
        );
    }

    public List<BookDTO> getAllBooksAsDTOs() {
        return bookRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public BookDTO getBookDTOById(Long id) {
        return toDTO(getBookById(id));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    } 

    @Override
    public Book addBook(Book book) {
        if (book.getId() != null && bookRepository.existsById(book.getId())) {
            throw new RuntimeException("Book already exists with id: " + book.getId());
        }
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Book borrowBook(Long bookId, Long memberId) {
        Book book = getBookById(bookId);
        if (book.isBorrowed()) {
            throw new IllegalStateException("Book is already borrowed");
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        book.borrow(member);

        return bookRepository.save(book);
    }

    @Override
    public Book returnBook(Long bookId) {
        Book book = getBookById(bookId);

        Member member = book.getBorrowedBy();
        if (member != null) {
            member.returnBook(book);
            memberRepository.save(member);
        }
        
        return bookRepository.save(book);    
    }
    
}
