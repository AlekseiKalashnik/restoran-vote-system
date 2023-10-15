package com.votingsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.votingsystem.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("select r from Restaurant r where upper(r.name) = upper(?1)")
    Optional<Restaurant> getByNameIgnoreCase(String description);

    @Query("select r from Restaurant r left join fetch r.dishes d where r.id=?1 and d.date=?2")
    Optional<Restaurant> getWithDishesWithDate(int id, LocalDate date);

    @Query("select r from Restaurant r left join fetch r.dishes d where d.date=?1 order by d.id")
    List<Restaurant> getAllWithDishes(LocalDate date);
}