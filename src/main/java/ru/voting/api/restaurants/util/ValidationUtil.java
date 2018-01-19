package ru.voting.api.restaurants.util;

import ru.voting.api.restaurants.model.BaseEntity;
import ru.voting.api.restaurants.util.exception.NotFoundException;

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

    public static void checkNew(BaseEntity bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
