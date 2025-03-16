package com.library.LibraryMangement.service;


import com.library.LibraryMangement.entity.Card;
import com.library.LibraryMangement.entity.CardStatus;
import com.library.LibraryMangement.entity.Student;
import com.library.LibraryMangement.repo.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    CardRepo cardRepo;


    public Card createCard(Student student){
        Card card =new Card();
        student.setCard(card);
        card.setStudent(student);
        cardRepo.save(card);
        return card;
    }
    public void deactivate(int student_id){
        cardRepo.deactivateCard(student_id, CardStatus.DEACTIVATED.toString());

    }
}
