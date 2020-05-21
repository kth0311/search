package com.book.search.controller;

import com.book.search.controller.dto.ErrorDto;
import com.book.search.exeception.ClientException;
import com.book.search.exeception.SearchException;
import com.book.search.exeception.ServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.function.Consumer;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @NotNull
    private final MessageSourceAccessor accessor;

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorDto> serverException(ServerException e) {
        return makeResponseEntity(e, x -> log.error(x.getMessage(), x));
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorDto> clientException(ClientException e) {
        return makeResponseEntity(e, x -> log.warn(x.getMessage(), x));
    }

    @ExceptionHandler(SearchException.class)
    public ResponseEntity<ErrorDto> vineException(SearchException e) {
        return makeResponseEntity(e, x -> log.warn(x.getMessage(), x));
    }

    private ResponseEntity<ErrorDto> makeResponseEntity(SearchException e, Consumer<SearchException> consumer) {
        consumer.accept(e);
        return ResponseEntity.status(e.getStatus())
                .body(make(e));
    }

    private ErrorDto make(SearchException e) {
        ErrorDto result = new ErrorDto(e.toString());
        result.setCode(e.getCode());
        result.setStatus(e.getStatus()
                .value());
        result.setMessage(makeMessage(e));
        return result;
    }

    private String makeMessage(SearchException e) {
        return accessor.getMessage(e.getMessageCode(),
                e.getMessageArguments(),
                e.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorDto unknownException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ErrorDto result = new ErrorDto(ex.toString());
        result.setCode(500000);
        result.setStatus(INTERNAL_SERVER_ERROR.value());
        result.setMessage(ex.getLocalizedMessage());
        return result;
    }
}