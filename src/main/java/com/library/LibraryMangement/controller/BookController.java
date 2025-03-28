package com.library.LibraryMangement.controller;


import com.library.LibraryMangement.entity.Book;
import com.library.LibraryMangement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/createBook")
    public ResponseEntity createBook(@RequestBody Book book){

        bookService.createBook(book);
        return new ResponseEntity("Book added to the library system", HttpStatus.ACCEPTED);

    }
    @GetMapping("/getBooks")
    public ResponseEntity getBooks(@RequestParam(value = "genre",required = false) String genre,
                                   @RequestParam(value = "available",required = false,defaultValue = "false") boolean available,
                                   @RequestParam(value = "author",required = false) String author){

        List<Book> bookList=bookService.getBooks(genre,available,author);
        return new ResponseEntity(bookList,HttpStatus.OK);




    }
}
