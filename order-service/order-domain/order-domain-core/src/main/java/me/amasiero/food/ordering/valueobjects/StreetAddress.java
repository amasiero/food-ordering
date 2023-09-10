package me.amasiero.food.ordering.valueobjects;

import java.util.UUID;

public record StreetAddress(UUID id, String street, String postalCode, String city) { }
