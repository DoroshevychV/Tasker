/**
 * @description Головний контроллер
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 17.10.2018 20:00
 **/
package com.tasker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/user")
    public String getUserPage(){
        return "user";
    }
}
