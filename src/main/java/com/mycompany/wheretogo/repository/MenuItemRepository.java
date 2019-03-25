package com.mycompany.wheretogo.repository;

import com.mycompany.wheretogo.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    @Override
    @Transactional
    MenuItem save(MenuItem menuItem);

    @Query("SELECT mi from MenuItem mi WHERE mi.date BETWEEN :startDate AND :endDate ORDER BY mi.date DESC, mi.dish.restaurant.name, mi.dish.name ASC")
    List<MenuItem> findAllBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
