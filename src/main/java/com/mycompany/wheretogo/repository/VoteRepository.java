package com.mycompany.wheretogo.repository;

import com.mycompany.wheretogo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Override
    @Transactional
    Vote save(Vote vote);

    @Override
    Optional<Vote> findById(Integer id);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.dateTime DESC")
    List<Vote> findAll(@Param("userId") Integer userId);
}
