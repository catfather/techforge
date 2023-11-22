package org.spring.dev.company.controller.chatbot;

import lombok.RequiredArgsConstructor;
import org.spring.dev.company.service.chatbot.KomoranService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chatbot")
@RequiredArgsConstructor
public class ChatbotController {

    private final KomoranService komoranService;

    @PostMapping("/bot")
    public String message(String message, Model model){

        model.addAttribute("msg", komoranService.analyze(message));

        return "/chatbot/bot-message";

    }

}
