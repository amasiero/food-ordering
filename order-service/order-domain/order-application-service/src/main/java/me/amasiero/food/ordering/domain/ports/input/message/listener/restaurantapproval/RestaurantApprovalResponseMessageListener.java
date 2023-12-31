package me.amasiero.food.ordering.domain.ports.input.message.listener.restaurantapproval;

import me.amasiero.food.ordering.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
