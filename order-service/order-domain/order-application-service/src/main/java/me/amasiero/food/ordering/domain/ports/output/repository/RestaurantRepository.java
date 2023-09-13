package me.amasiero.food.ordering.domain.ports.output.repository;

import me.amasiero.food.ordering.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findByRestauratnInformation(Restaurant restaurant);
}
