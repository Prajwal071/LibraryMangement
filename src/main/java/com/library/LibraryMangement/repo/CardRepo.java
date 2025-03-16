package com.library.LibraryMangement.repo;


import com.library.LibraryMangement.entity.Card;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Transactional
public interface CardRepo extends JpaRepository<Card,Integer> {

    @Modifying
    @Query
            (value = "update card c set c.card_status=:new_card_status where c.id in(select card_id from student s where s.id=:student_id)",nativeQuery = true)
    void deactivateCard(@Param("student_id") int student_id,@Param("new_card_status") String new_card_status);

}

