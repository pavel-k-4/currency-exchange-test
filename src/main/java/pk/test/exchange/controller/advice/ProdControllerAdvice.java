package pk.test.exchange.controller.advice;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.function.Supplier;

@ControllerAdvice
@Profile("!dev")
public class ProdControllerAdvice {

    @ModelAttribute("css")
    public Supplier<String> cssPath() {
        return () -> "/css/style.css";
    }

    @ModelAttribute("js")
    public Supplier<String> jsPath() {
        return () -> "/js/script.js";
    }
}
