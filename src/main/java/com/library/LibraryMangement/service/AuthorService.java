package com.library.LibraryMangement.service;


import com.library.LibraryMangement.entity.Author;
import com.library.LibraryMangement.repo.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepo authorRepo;


    public void createAuthor(Author author){
        authorRepo.save(author);

    }
    public void updateAuthor(Author author){
        authorRepo.updateAuthorDetails(author);
    }

    public void deleteAuthor(int id ){
        authorRepo.deleteCustom(id);
    }
}
