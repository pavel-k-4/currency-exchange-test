package pk.test.exchange.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pk.test.exchange.dto.NewUserDto;
import pk.test.exchange.service.UserService;

import javax.validation.Valid;

@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("dto", new NewUserDto());
        log.info("Registration form opened");
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(Model model, @Valid @ModelAttribute("dto") NewUserDto dto) {
        userService.createUser(dto).ifPresentOrElse((error) -> {
                model.addAttribute("error", error.getExplanation());
                model.addAttribute("success", false);
        }, () ->
                model.addAttribute("success", true)
        );
        return "register";
    }

}
