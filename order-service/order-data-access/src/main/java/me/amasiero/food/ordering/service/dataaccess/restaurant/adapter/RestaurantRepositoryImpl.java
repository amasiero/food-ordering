package me.amasiero.food.ordering.service.dataaccess.restaurant.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.ports.output.repository.RestaurantRepository;
import me.amasiero.food.ordering.entity.Restaurant;
import me.amasiero.food.ordering.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper;
import me.amasiero.food.ordering.service.dataaccess.restaurant.repository.RestaurantJpaRepository;

@Component
@AllArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    @Override
    public Optional<Restaurant> findByRestauratnInformation(Restaurant restaurant) {
        List<UUID> restaurantProducts = restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant);
        return restaurantJpaRepository.findByRestaurantIdAndProductIdIn(restaurant.getId().getValue(), restaurantProducts)
                                      .map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
