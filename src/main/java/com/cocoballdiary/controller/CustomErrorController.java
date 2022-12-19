package com.cocoballdiary.controller;

import com.cocoballdiary.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping(value="/error", method = {RequestMethod.GET, RequestMethod.POST})
	public String handleError(HttpServletResponse response, Model model) {

		HttpStatus status = HttpStatus.valueOf(response.getStatus());
		ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_SERVER_ERROR;

		model.addAttribute("status", status);
		model.addAttribute("errorCode", errorCode);
		model.addAttribute("message", errorCode.getMessage(status.getReasonPhrase()));

        return "error/error";

	}
}
