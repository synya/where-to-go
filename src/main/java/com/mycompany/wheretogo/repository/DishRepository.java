package com.mycompany.wheretogo.repository;

import com.mycompany.wheretogo.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Override
    @Transactional
    Dish save(Dish dish);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.name ASC")
    List<Dish> findAll(@Param("restaurantId") Integer restaurantId);

}
