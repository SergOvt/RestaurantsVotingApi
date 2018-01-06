package ru.voting.api.restaurants.util;

import ru.voting.api.restaurants.util.exception.NotFoundException;
import ru.voting.api.restaurants.util.exception.VotingAccessException;

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
            throw new VotingAccessException("Voting time is out");
        }
    }
}
