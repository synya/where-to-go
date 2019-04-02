package com.mycompany.wheretogo.repository;

import com.mycompany.wheretogo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Override
    @Transactional
    Vote save(Vote vote);

    @Override
    Optional<Vote> findById(Integer id);

    @Query("SELECT v from Vote v WHERE v.user.id=:userId AND v.date=:date")
    Optional<Vote> findByIdAndDate(@Param("userId") Integer userId, @Param("date") LocalDate date);

    @Query("SELECT v from Vote v WHERE v.user.id=:userId ORDER BY v.date DESC")
    List<Vote> findAll(@Param("userId") int userId);

    @Query("SELECT v from Vote v WHERE v.user.id=:userId AND v.date BETWEEN :startDate AND :endDate ORDER BY v.date DESC")
    List<Vote> findAllBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") int userId);
}
