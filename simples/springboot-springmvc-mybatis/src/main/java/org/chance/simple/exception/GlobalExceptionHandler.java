package org.chance.simple.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * http://for-get.github.io/http-decision-diagram/httpdd.fsm.html
 * <p>
 * https://developer.github.com/v3/
 * <p>
 * <p>
 * <p>
 * 600                              <-- Status Code，表示这是一个业务错误，监控看这里
 * X-Biz-Err: ERR_USER_NOT_FOUND    <-- 这是一个自定义的表达业务错误代码的header
 * {                                <-- 返回体，json格式
 * "msg": "找不到该用户"            <-- 对于一个错误，保证总有个错误信息向用户展示
 * "data": { ... }                <-- 如果有必要，附带一些前端需要向用户展现的报错的其他数据
 * }
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_MESSAGE = "handle exception:{} for uri:{}, device:{}";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String[] supportedMethods = ex.getSupportedMethods();
        String msg = "supported methods: " + StringUtils.arrayToDelimitedString(supportedMethods, "; ");

        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(msg).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
        if (mediaTypes != null && (!mediaTypes.isEmpty())) {
            headers.set("Accept", MediaType.toString(mediaTypes));
        }
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String msg = "";

        if (ex instanceof MissingServletRequestParameterException) {
            msg = "missing parameter: " + ((MissingServletRequestParameterException) ex).getParameterName();
        } else {
            msg = "exception message: " + ex.getMessage();
        }

        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(msg).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    /**
     * 当body里面的参数校验不通过的时候会报这个错误
     * MethodArgumentNotValidException
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error body = Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build();
        Iterator<ObjectError> iterator = ex.getBindingResult().getAllErrors().iterator();
        Map<String, String> detail = new HashMap<>(ex.getBindingResult().getAllErrors().size());
        while (iterator.hasNext()) {
            ObjectError error = iterator.next();
            if (error instanceof FieldError) {
                detail.put(error.getObjectName() + "." + ((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                detail.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        body.setMsg(detail.entrySet().iterator().next().getValue());
        body.setData(detail);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Error body = Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build();
        Iterator<ObjectError> iterator = ex.getBindingResult().getAllErrors().iterator();
        Map<String, String> detail = new HashMap<>(ex.getBindingResult().getAllErrors().size());
        while (iterator.hasNext()) {
            ObjectError error = iterator.next();
            if (error instanceof FieldError) {
                detail.put(error.getObjectName() + "." + ((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                detail.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        body.setMsg(detail.entrySet().iterator().next().getValue());
        body.setData(detail);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build(), headers, status, request);
    }

    /**
     * spring未处理的其他系统异常
     * RequestParam方法参数校验失败的异常
     * javax.validation.ConstraintViolationException
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Error handleException(ConstraintViolationException ex, HttpServletRequest request) {
        Error body = Error.builder().code(HttpStatus.BAD_REQUEST.value()).msg(ex.getMessage()).build();
        Iterator<ConstraintViolation<?>> iterator = ex.getConstraintViolations().iterator();
        Map<String, String> detail = new HashMap<>(ex.getConstraintViolations().size());
        while (iterator.hasNext()) {
            ConstraintViolation<?> constraintViolation = iterator.next();
            detail.put(String.format("%s.%s", constraintViolation.getRootBeanClass().getSimpleName(), constraintViolation.getPropertyPath().toString()), constraintViolation.getMessage());
        }
        body.setMsg(detail.entrySet().iterator().next().getValue());
        body.setData(detail);
        return body;
    }

    /**
     * ********************* 自定义异常 *************************
     */

//    @ResponseStatus(BizException.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(BizException.class)
//    public Error handleServerInternalException(BizException ex, WebRequest request) {
//        return Error.builder().code(1).msg("message").detail("detail").build();
//    }
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Error> handleServerInternalException(BizException ex, WebRequest request) {
        return ResponseEntity.status(BizException.httpStatus).header("X-Biz-Err", "ERR_USER_NOT_FOUND").body(Error.builder().code(1).msg("message").data("detail").build());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Error handleException(Exception ex, HttpServletRequest request) {
        return Error.builder().msg(ex.getMessage()).build();
    }

}
