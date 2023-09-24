package me.amasiero.food.ordering.service.dataaccess.restaurant.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.amasiero.food.ordering.service.dataaccess.restaurant.entity.RestaurantEntity;
import me.amasiero.food.ordering.service.dataaccess.restaurant.entity.RestaurantEntityId;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, RestaurantEntityId> {

    Optional<List<RestaurantEntity>> findByRestaurantIdAndProductIdIn(UUID restaurantId, List<UUID> productIds);
}
