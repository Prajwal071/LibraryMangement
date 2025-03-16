package com.library.LibraryMangement.repo;


import com.library.LibraryMangement.entity.Transaction;
import jakarta.transaction.Transactional;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TransactionRepo extends JpaRepository<Transaction,Integer> {

    @Query("select t from Transaction t where t.card.id=:card_id and t.book.id=:book_id and t.transactionStatus=:status and t.isIssueOperation=:isIssue")

    List<Transaction> findByCard_Book(@Param("card_id") int cardId,
                                      @Param("book_id") int bookId,
                                      @Param("status") com.library.LibraryMangement.entity.TransactionStatus transactionStatus,
                                      @Param("isIssue") boolean isIssue);


}
