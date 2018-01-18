package ru.voting.api.restaurants.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.voting.api.restaurants.util.ValidationUtil;
import ru.voting.api.restaurants.util.exception.ErrorInfo;
import ru.voting.api.restaurants.util.exception.ErrorType;
import ru.voting.api.restaurants.util.exception.NotFoundException;
import ru.voting.api.restaurants.util.exception.VotingAccessException;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.util.StringJoiner;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo notFoundError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.LOCKED)
    @ExceptionHandler(VotingAccessException.class)
    public ErrorInfo votingError(HttpServletRequest req, VotingAccessException e) {
        return logAndGetErrorInfo(req, e, false, ErrorType.DATA_ERROR);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler({DataIntegrityViolationException.class, PersistenceException.class})
    public ErrorInfo persistenceError(HttpServletRequest req, PersistenceException e) {
        return logAndGetErrorInfo(req, e, true, ErrorType.DATA_ERROR);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo validationError(HttpServletRequest req, MethodArgumentNotValidException e) {
        StringJoiner joiner = new StringJoiner(", ", e.getClass().getName() + ": ", "");
        e.getBindingResult().getFieldErrors().forEach(
                fe -> joiner.add(fe.getDefaultMessage()));
        log.warn("{} at request  {}: {}", ErrorType.DATA_ERROR, req.getRequestURL(), joiner.toString());
        return new ErrorInfo(req.getRequestURL(), ErrorType.DATA_ERROR, joiner.toString());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, ErrorType.APP_ERROR);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType, rootCause.toString());
    }
}