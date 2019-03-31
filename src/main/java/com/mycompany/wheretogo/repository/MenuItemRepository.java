package com.mycompany.wheretogo.repository;

import com.mycompany.wheretogo.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM MenuItem mi WHERE mi.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    MenuItem save(MenuItem menuItem);

    @Query("SELECT mi from MenuItem mi WHERE mi.date=:date ORDER BY mi.date DESC, mi.dish.restaurant.name, mi.dish.name ASC")
    List<MenuItem> findAllByDate(@Param("date") LocalDate date);

    @Query("SELECT mi from MenuItem mi WHERE mi.date BETWEEN :startDate AND :endDate ORDER BY mi.date DESC, mi.dish.restaurant.name, mi.dish.name ASC")
    List<MenuItem> findAllBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
