package com.library.LibraryMangement.service;

import com.library.LibraryMangement.entity.Card;
import com.library.LibraryMangement.entity.Student;
import com.library.LibraryMangement.repo.CardRepo;
import com.library.LibraryMangement.repo.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private CardService cardService;

    public void createStudent(Student student) {
        // Save the student first
        studentRepo.save(student);

        // Create and assign a card
        Card card = cardService.createCard(student);
        cardRepo.save(card);

        logger.info("The card for the student {} is created with the card details {}", student.getId(), card.getId());
    }

    public int updateStudent(Student student) {
        return studentRepo.updateStudentDetails(student);
    }

    public void deleteStudent(int id) {
        // First deactivate the card before deleting the student
        cardService.deactivate(id);

        // Then delete the student
        studentRepo.deleteCustom(id);

        logger.info("Student with ID {} and their associated card have been deleted.", id);
    }
}
