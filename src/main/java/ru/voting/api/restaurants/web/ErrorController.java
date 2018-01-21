package ru.voting.api.restaurants.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.voting.api.restaurants.util.exception.ErrorInfo;
import ru.voting.api.restaurants.util.exception.ErrorType;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController {

    private static Logger log = LoggerFactory.getLogger(ErrorController.class);

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @RequestMapping(value = "unauthorised", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ErrorInfo unauthorisedError(HttpServletRequest req) {
        log.info("{}: Unauthorised request", ErrorType.ACCESS_ERROR);
        return new ErrorInfo("", ErrorType.ACCESS_ERROR, "Unauthorised request");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping(value = "forbidden", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ErrorInfo forbiddenError () {
        log.info("{}: Access denied", ErrorType.ACCESS_ERROR);
        return new ErrorInfo("", ErrorType.ACCESS_ERROR, "Access denied");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(value = "notfound", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ErrorInfo notFoundError() {
        log.info("{}: Not found", ErrorType.ACCESS_ERROR);
        return new ErrorInfo("", ErrorType.ACCESS_ERROR, "Not found");
    }
}
