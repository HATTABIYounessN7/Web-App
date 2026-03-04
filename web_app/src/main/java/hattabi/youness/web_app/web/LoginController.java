package hattabi.youness.web_app.web;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
