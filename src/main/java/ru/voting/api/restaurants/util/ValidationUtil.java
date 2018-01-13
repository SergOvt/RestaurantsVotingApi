package ru.voting.api.restaurants.util;

import ru.voting.api.restaurants.model.BaseEntity;
import ru.voting.api.restaurants.util.exception.NotFoundException;
import ru.voting.api.restaurants.util.exception.VotingAccessException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFound(T object, int id) {
        return checkNotFound(object, "Not found entity with id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, int id) {
        checkNotFound(found, "Not found entity with id=" + id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException(msg);
        }
    }

    public static void checkVotingAccess(boolean access) {
        if (!access) {
            throw new VotingAccessException("Not allowed to vote");
        }
    }

    public static void checkNew(BaseEntity bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }
}
