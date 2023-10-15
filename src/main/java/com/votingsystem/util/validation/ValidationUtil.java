package com.votingsystem.util.validation;

import com.votingsystem.HasId;
import com.votingsystem.error.IllegalRequestDataException;
import com.votingsystem.model.Restaurant;
import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;

import java.time.LocalTime;
import java.util.Optional;

@UtilityClass
public class ValidationUtil {

    public static LocalTime FRONTIER = LocalTime.of(11, 0);

    public static void setFRONTIER(LocalTime time) {
        ValidationUtil.FRONTIER = time;
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    public static void checkTime() {
        if (LocalTime.now().isAfter(FRONTIER)) {
            throw new IllegalRequestDataException("You can't change your vote after 11!");
        }
    }

    public static void checkForNull(Object e) {
        if (e != null) {
            throw new IllegalRequestDataException("It is already exist!");
        }
    }

    public static void checkForNotNull(Object e) {
        if (e == null) {
            throw new IllegalRequestDataException("Entity not found!");
        }
    }

    public static void checkEmpty(Optional<Restaurant> list) {
        if (list.isEmpty()) {
            throw  new IllegalRequestDataException("Not found at this date");
        }
    }
}