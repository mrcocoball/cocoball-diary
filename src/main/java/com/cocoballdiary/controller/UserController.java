package com.cocoballdiary.controller;

import com.cocoballdiary.dto.UserJoinDto;
import com.cocoballdiary.dto.UserModifyDto;
import com.cocoballdiary.dto.security.dto.UserSecurityDto;
import com.cocoballdiary.exception.DiaryException;
import com.cocoballdiary.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public void joinGet() {

    }

    @PostMapping("/join")
    public String joinPost(UserJoinDto userJoinDto, RedirectAttributes redirectAttributes) {

        try {

            userService.join(userJoinDto);

        } catch (DiaryException e) {

            redirectAttributes.addFlashAttribute("error", "uid");
            return "redirect:/user/join";
        }

        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public void loginGet(String error, String logout) {

    }

    @GetMapping("/modify")
    public void modifyGet(@AuthenticationPrincipal UserSecurityDto userSecurityDto, Model model) {

        UserModifyDto userModifyDto = userService.get(userSecurityDto.getUid());

        model.addAttribute("dto", userModifyDto);
    }

    @PostMapping("/modify")
    public String modifyPost(UserModifyDto userModifyDto) {

        userService.modify(userModifyDto);

        return "redirect:/diary/list";
    }


}
