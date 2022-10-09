package pk.test.exchange.controller.advice;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;
import java.util.function.Supplier;

/**
 * Avoid web browser caching
 */
@ControllerAdvice
@Profile("dev")
public class DevControllerAdvice {

    @ModelAttribute("css")
    public Supplier<String> cssPath() {
        return () -> "/css/style.css?v="+new Date();
    }

    @ModelAttribute("js")
    public Supplier<String> jsPath() {
        return () -> "/js/script.js?v="+new Date();
    }
}
