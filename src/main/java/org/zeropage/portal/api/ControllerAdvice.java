package org.zeropage.portal.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zeropage.portal.exception.FileNotSavableException;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;

@RestControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(FileNotFoundException.class)
    public String fileNotFoundHandler(FileNotFoundException e) {
        return "파일을 찾을 수 없습니다.";
    }

    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    @ExceptionHandler(MalformedURLException.class)
    public String malformedURLHandler(MalformedURLException e) {
        return "파일을 읽을 수 없습니다. 다시 시도해 주세요.";
    }

    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    @ExceptionHandler(FileNotSavableException.class)
    public String fileNotSavedHandler(FileNotSavableException e) {
        return "파일 저장에 실패했습니다. 다시 시도해 주세요.";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String fileNotSavedHandler(Exception e) {
        return "에러가 발생하였습니다. 다시 시도해 주세요. 같은 에러가 반복되면, 회장단이나 관리자에게 문의해 주세요.";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementHandler(NoSuchElementException e) {
        return "파일이나 데이터를 찾을 수 없습니다. 링크가 올바른지, 파일이나 데이터가 삭제되지는 않았는지 확인 부탁드립니다.";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String dataIntegrityViolationHandler(DataIntegrityViolationException e) {
        return "데이터가 유효하지 않습니다.";
    }
}