package com.votingsystem.web.restaurant;

import com.votingsystem.model.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/api/restaurant";

    @Override
    @GetMapping("/{id}/with-today-dishes")
    public ResponseEntity<Restaurant> getWithTodayDish(@PathVariable int id) {
        return super.getWithTodayDish(id);
    }

    @Override
    @GetMapping("/all-with-dishes")
    @Cacheable
    public List<Restaurant> getAllWithTodayDishes() {
        return super.getAllWithTodayDishes();
    }
}
