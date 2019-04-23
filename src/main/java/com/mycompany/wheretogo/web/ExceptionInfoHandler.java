package com.mycompany.wheretogo.web;

import com.mycompany.wheretogo.util.ValidationUtil;
import com.mycompany.wheretogo.util.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static com.mycompany.wheretogo.util.ValidationUtil.getRootCause;
import static com.mycompany.wheretogo.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) //422
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo notFound(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) //422
    @ExceptionHandler(VotingRulesException.class)
    public ErrorInfo votingRules(HttpServletRequest req, VotingRulesException e) {
        return logAndGetErrorInfo(req, e, false, VOTING_RULES_ERROR);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT) // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e, true, VALIDATION_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalRequestDataException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            return getBindingErrorInfo(req, ((MethodArgumentNotValidException) e).getBindingResult());
        } else {
            return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
        }
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... messages) {
        Throwable rootCause = getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType,
                messages.length == 0 ? List.of(ValidationUtil.getMessage(rootCause)) : List.of(messages));
    }

    private static ErrorInfo getBindingErrorInfo(HttpServletRequest req, BindingResult result) {
        ErrorInfo errorInfo = new ErrorInfo(req.getRequestURL(), VALIDATION_ERROR, new ArrayList<>());
        result.getFieldErrors().forEach(
                fe -> {
                    String msg = fe.getDefaultMessage();
                    if (msg != null) {
                        if (!msg.startsWith(fe.getField())) {
                            msg = fe.getField() + ' ' + msg;
                        }
                        errorInfo.addDetail(msg);
                    }
                });
        return errorInfo;
    }
}
