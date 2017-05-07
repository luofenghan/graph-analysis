package com.analysis.graph.web.api;

import com.analysis.graph.common.domain.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;


/**
 * Created by cwc on 2017/4/6 0006.
 */
@RestControllerAdvice
public class RestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorDTO handleIllegalArgumentException(IllegalArgumentException e) {
        return generateErrorDTO(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ErrorDTO handleIllegalStateException(IllegalStateException e) {
        return generateErrorDTO(HttpStatus.UNAUTHORIZED, e);
    }

    @ExceptionHandler(SQLException.class)
    public ErrorDTO handleSQLException(SQLException e) {
        return generateErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    private ErrorDTO generateErrorDTO(HttpStatus httpStatus, Exception e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(httpStatus.getReasonPhrase());
        errorDTO.setCode(httpStatus.value());
        errorDTO.setTitle(e.getMessage());
        errorDTO.setDetail("");
        return errorDTO;
    }

    /**
     * 用于全局绑定键值到model里面，全局的@RequestMapping都能或得到此处设置的键值对
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(ModelAndView model) {
        /*if (model != null && !model.getViewName().startsWith("redirect:")) {

        }*/
    }

    /**
     * 用来设置WebDataBinder， 用来自定绑定前台请求参数到Model中
     *
     * @param webDataBinder
     * @see {http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/WebDataBinder.html}
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
    }
}
