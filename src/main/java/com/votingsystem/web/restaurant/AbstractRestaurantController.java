package com.votingsystem.web.restaurant;

import com.votingsystem.repository.RestaurantRepository;
import com.votingsystem.util.validation.ValidationUtil;
import com.votingsystem.model.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class AbstractRestaurantController {

    @Autowired
    private RestaurantRepository repository;

    public ResponseEntity<Restaurant> getWithTodayDish(int id) {
        log.info("get rest{} with today dishes", id);
        ValidationUtil.assureIdConsistent(repository.getById(id), id);
        return ResponseEntity.of(repository.getWithDishesWithDate(id, LocalDate.now()));
    }

    public List<Restaurant> getAllWithTodayDishes() {
        log.info("getAllWithTodayDishes");
        return repository.getAllWithDishes(LocalDate.now());
    }
}
