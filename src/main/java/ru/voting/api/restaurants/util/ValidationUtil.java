package ru.voting.api.restaurants.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.voting.api.restaurants.util.exception.NotFoundException;
import ru.voting.api.restaurants.util.exception.VotingAccessException;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFound(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkVotingAccess(boolean access) {
        if (!access) {
            throw new VotingAccessException("Not allowed to vote");
        }
    }

    public static ResponseEntity checkExceptions (ServiceExecutor executor) {
        try {
            return executor.execute();
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
                    .body("Incorrect data in request body");
        }
    }
}
