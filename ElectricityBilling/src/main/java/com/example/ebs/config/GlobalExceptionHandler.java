package com.example.ebs.config;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.ui.Model; import org.springframework.web.bind.annotation.ControllerAdvice; import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice public class GlobalExceptionHandler {
  private static final Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);
  @ExceptionHandler(Exception.class) public String handle(Model m, Exception ex){ log.error("Error", ex); m.addAttribute("message", ex.getMessage()); return "error"; }
}
