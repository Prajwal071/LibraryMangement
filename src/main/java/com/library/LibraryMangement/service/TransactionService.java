package com.library.LibraryMangement.service;

import com.library.LibraryMangement.entity.*;
import com.library.LibraryMangement.repo.BookRepo;
import com.library.LibraryMangement.repo.CardRepo;
import com.library.LibraryMangement.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    BookRepo bookRepo;
    @Autowired
    CardRepo cardRepo;
    @Value("${books.max_allowed:5}")
    int max_allowed_books;
    @Value("${books.max_allowed_days}")
    int max_days_allowed;
    @Value("${books.fine.per_day}")
    int fine_per_day;


    public String issueBooks(int cardId, int bookId) throws Exception {
        Book book = bookRepo.findById(bookId).get();
        System.out.println(book);

        if (book == null || book.isAvailable() != true) {
            throw new Exception("Book is either unavailable or not present!!");
        }
        Card card = cardRepo.findById(cardId).get();
        System.out.println(card);
        if (card == null || card.getCardStatus() == CardStatus.DEACTIVATED) {
            throw new Exception("Card is invalid!!");
        }
        if (card.getBooks().size() > max_allowed_books) {
            throw new Exception("Book limit reached for this card!!");
        }
        book.setAvailable(false);
        book.setCard(card);
        List<Book> books = card.getBooks();
        books.add(book);
        card.setBooks(books);
        bookRepo.updateBook(book);
        Transaction transaction = new Transaction();
        transaction.setCard(card);
        transaction.setBook(book);
        transaction.setIssueOperation(true);
        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepo.save(transaction);
        return transaction.getTransactionId();
    }

    public String returnBooks(int cardId, int bookId) throws Exception {
        List<Transaction> transactions = transactionRepo.findByCard_Book(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction last_issue_transaction = transactions.get(transactions.size() - 1);
        //Last transaction that has been done ^^^^
        Date issueDate = last_issue_transaction.getTransactionDate();
        Long issueTime = Math.abs(issueDate.getTime() - System.currentTimeMillis());
        long number_of_days_passed = TimeUnit.DAYS.convert(issueTime, TimeUnit.MILLISECONDS);
        int fine = 0;
        if (number_of_days_passed > max_days_allowed) {
            fine = (int) Math.abs(number_of_days_passed - max_days_allowed) * fine_per_day;
        }
        Card card = last_issue_transaction.getCard();
        Book book = last_issue_transaction.getBook();
        book.setCard(null);
        book.setAvailable(true);
        bookRepo.updateBook(book);
        Transaction new_transaction = new Transaction();
        new_transaction.setBook(book);
        new_transaction.setCard(card);
        new_transaction.setFineAmount(fine);
        new_transaction.setIssueOperation(false);
        new_transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepo.save(new_transaction);
        return new_transaction.getTransactionId();

    }
}
