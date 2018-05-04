package org.launchcode.cheesemvc.controllers;


import org.launchcode.cheesemvc.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {


    @RequestMapping(value = "add", method=RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("user", new User());
        return "user/add";
    }

    @RequestMapping(value ="add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid User user, Errors errors){

        if(errors.hasErrors())
        {
            model.addAttribute(user);

            return "user/add";
        }

        model.addAttribute("title", "Welcome ");
        model.addAttribute("un",user.getUsername());
        return "user/index";
    }


}
