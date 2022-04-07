package pl.coderslab.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@Secured("ROLE_ADMIN")
public class GlobalMethodSecurityTest {

    @GetMapping("/deleteall")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    private String deleteAllForbiddenMethod(){
        return "Forbidden method access";
    };
}
