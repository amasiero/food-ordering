package me.amasiero.food.ordering.service.dataaccess.restaurant.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import me.amasiero.food.ordering.domain.valueobjects.Money;
import me.amasiero.food.ordering.domain.valueobjects.ProductId;
import me.amasiero.food.ordering.domain.valueobjects.RestaurantId;
import me.amasiero.food.ordering.entity.Product;
import me.amasiero.food.ordering.entity.Restaurant;
import me.amasiero.food.ordering.service.dataaccess.restaurant.entity.RestaurantEntity;
import me.amasiero.food.ordering.service.dataaccess.restaurant.exception.RestaurantDataAccessException;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getProducts()
                         .stream()
                         .map(product -> product.getId().getValue())
                         .toList();
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity = restaurantEntities.stream()
                                                              .findFirst()
                                                              .orElseThrow(() ->
                                                                  new RestaurantDataAccessException("Restaurant could not be found.")
                                                              );

        List<Product> restaurantProducts = restaurantEntities.stream()
                                                             .map(entity -> Product.builder()
                                                                                   .id(new ProductId(entity.getProductId()))
                                                                                   .name(entity.getProductName())
                                                                                   .price(new Money(entity.getProductPrice()))
                                                                                   .build()
                                                             )
                                                             .collect(Collectors.toList());

        return Restaurant.builder()
                         .id(new RestaurantId(restaurantEntity.getRestaurantId()))
                         .products(restaurantProducts)
                         .active(restaurantEntity.getRestaurantActive())
                         .build();
    }
}
