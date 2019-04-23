package com.mycompany.wheretogo.util;

import com.mycompany.wheretogo.HasId;
import com.mycompany.wheretogo.util.exception.NotFoundException;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, " with id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, " with id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity" + msg);
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }
}