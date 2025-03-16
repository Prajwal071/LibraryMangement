package com.library.LibraryMangement.service;


import com.library.LibraryMangement.entity.Book;
import com.library.LibraryMangement.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    public void createBook(Book book) {
        bookRepo.save(book);


    }

    //Either giving you all the available books
    //OR gicing you all the non-available books
    public List<Book> getBooks(String genre, boolean isAvailable, String author) {

        if (genre != null && author != null) {
            return bookRepo.findBooksByGenre_Author(genre, author, isAvailable);
        } else if (genre != null) {
            return bookRepo.findBooksByGenre(genre, isAvailable);
        } else if (author != null) {
            return bookRepo.findBooksByAuthor(author, isAvailable);
        }
        return bookRepo.findBooksByAvailability(isAvailable);


    }
}
