package example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import example.app.EchoForm;

@Controller
public class LoginController {
	
	@RequestMapping(path = {"/", "/index"})
	public String viewInput(Model model) {
		return "index";
	}
	
}
