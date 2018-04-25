package org.launchcode.cheesemvc.controllers;


import org.launchcode.cheesemvc.models.Cheese;
import org.launchcode.cheesemvc.models.CheeseData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("cheese")
public class CheeseController {






    @RequestMapping(value = "")
    public String index(Model model){

        model.addAttribute("cheeses", CheeseData.getAll());
        model.addAttribute("title","My Cheeses");
        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model){
        model.addAttribute("title", "Add Cheese");
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute Cheese nCheese){
        CheeseData.add(nCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model){

        model.addAttribute("cheeses", CheeseData.getAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";

    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseId){

        for(int s : cheeseId) {
            CheeseData.remove(s);
        }

        return "redirect:";
    }


    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int cheeseId){
        model.addAttribute("cheese", CheeseData.getById(cheeseId));
        model.addAttribute("title", "Edit Cheese " + CheeseData.getById(cheeseId).getName() + " (id="
                + cheeseId + ")");
        return"cheese/edit";
    }


    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditForm(int cheeseId, String name, String description){

        CheeseData.getById(cheeseId).setName(name);
        CheeseData.getById(cheeseId).setDescription(description);

        return "redirect:/cheese";
    }

}
