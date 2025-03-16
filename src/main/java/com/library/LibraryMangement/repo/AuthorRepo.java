package com.library.LibraryMangement.repo;

import com.library.LibraryMangement.entity.Author;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Transactional
public interface AuthorRepo extends JpaRepository<Author,Integer> {

    @Modifying
    @Query("update Author a set a.name=:#{#new_author.name}," +
            "a.email=:#{#new_author.email}," +
            "a.age=:#{#new_author.age}," +
            "a.country=:#{#new_author.country} where a.id=:#{#new_author.id}")
    int updateAuthorDetails(@Param("new_author") Author new_author);

    @Modifying
    @Query("delete Author a where a.id=:given_id")
    int deleteCustom(@Param("given_id") int id);
}
