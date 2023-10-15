package com.votingsystem.web.restaurant;

import com.votingsystem.repository.RestaurantRepository;
import com.votingsystem.to.RestaurantTo;
import com.votingsystem.web.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UniqueNameValidator implements Validator {
    private final RestaurantRepository restaurantRepository;
    private final HttpServletRequest request;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return RestaurantTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        RestaurantTo restaurant = (RestaurantTo) target;
        if (StringUtils.hasText(restaurant.getName())) {
            restaurantRepository.getByNameIgnoreCase(restaurant.getName())
                    .ifPresent(dbRestaurant ->
                    {
                        if (request.getMethod().equals("PUT")) {
                            int dbId = dbRestaurant.id();

                            if (restaurant.getId() != null && dbId == restaurant.id()) return;

                            String requestURI = request.getRequestURI();
                            if (requestURI.endsWith("/" + dbId))
                                return;
                        }
                        errors.rejectValue("name", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_NAME_OF_RESTAURANT);
                    });
        }
    }
}
