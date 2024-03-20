package dev.kush.securityall.controllers;

import dev.kush.securityall.dto.*;
import dev.kush.securityall.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    private final UserService userService;

    public DemoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home() {
        return "this is home page.";
    }

    @GetMapping("/user")
    public String user() {
        return "this is user page.";
    }

    @GetMapping("/admin")
    public String admin() {
        return "this is admin page.";
    }

    @GetMapping("/demo")
    public String demo() {
        return "this is demo page.";
    }

    @GetMapping("/root")
    public String root() {
        return "this is root page.";
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody(required = false) SignInDto signInDto,
                         @RequestHeader(name = "x-secret-key",required = false) String header) {
        return userService.signIn(signInDto,header);
    }

}
