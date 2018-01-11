package ru.voting.api.restaurants.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.voting.api.restaurants.util.exception.NotFoundException;
import ru.voting.api.restaurants.util.exception.VotingAccessException;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.function.Supplier;

public class ErrorsHandler {

    private ErrorsHandler() {
    }

    public static ResponseEntity checkExceptions (Supplier<ResponseEntity> supplier) {
        try {
            return supplier.get();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_HTML)
                    .body(e.getMessage());
        } catch (VotingAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.TEXT_HTML)
                    .body(e.getMessage());
        } catch (PersistenceException | ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.TEXT_HTML)
                    .body("Incorrect data in request");
        }
    }
}
