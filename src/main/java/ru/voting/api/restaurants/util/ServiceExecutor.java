package ru.voting.api.restaurants.util;

import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface ServiceExecutor {
    ResponseEntity execute ();
}
