package org.spring.dev.company.controller.approval;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApprovalMainController {

    @GetMapping("/approval/create")
    public String create() {
        return "/approval/ApprovalWrite";
    }

    @GetMapping("/approval/list")
    public String list() {
        return "/approval/ApprovalList";
    }

    @GetMapping("/approval/project")
    public String projectList() {
        return "/approval/ProjectList";
    }

    @GetMapping("/approval/pay")
    public String payList() {
        return "/approval/PayList";
    }
}
