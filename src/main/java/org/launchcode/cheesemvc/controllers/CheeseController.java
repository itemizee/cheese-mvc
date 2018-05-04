package org.launchcode.cheesemvc.controllers;


import org.launchcode.cheesemvc.models.Category;
import org.launchcode.cheesemvc.models.Cheese;
import org.launchcode.cheesemvc.models.data.CategoryDao;
import org.launchcode.cheesemvc.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private CategoryDao categoryDao;


    @RequestMapping(value = "")
    public String index(Model model){

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title","My Cheeses");
        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model){
        model.addAttribute("title", "Add Cheese");
        model.addAttribute("cheese", new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(
            @ModelAttribute @Valid Cheese nCheese, Errors errors, @RequestParam int categoryId, Model model){

        if(errors.hasErrors()){
            model.addAttribute("title", "Add Cheese");
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/add";
        }

        Optional<Category> cat = categoryDao.findById(categoryId);
        Category c = cat.get();
        nCheese.setCategory(c);
        cheeseDao.save(nCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model){

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";

    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseId){

        for(int s : cheeseId) {
            cheeseDao.deleteById(s);
        }

        return "redirect:";
    }


    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int cheeseId){

        Optional<Cheese> c = cheeseDao.findById(cheeseId);
        Cheese ch = c.get();

        model.addAttribute("cheese", ch); //CheeseData.getById(cheeseId))
        model.addAttribute("title", "Edit Cheese " + ch.getName() + " (id="
                + cheeseId + ")"); //CheeseData.getById(cheeseId).getName()
        model.addAttribute("categories", categoryDao.findAll());
        return"cheese/edit";
    }


    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditForm(@PathVariable int cheeseId, @ModelAttribute @Valid Cheese testCheese, Errors errors, Model model){

        Optional<Cheese> c = cheeseDao.findById(cheeseId);
        Cheese ch = c.get();

        if(errors.hasErrors())
        {
            model.addAttribute("title", "Edit Cheese " + ch.getName()  + " (id="
                    + cheeseId + ")"); //CheeseData.getById(cheeseId).getName()
            model.addAttribute("categories", categoryDao.findAll());

            return"cheese/edit";
        }



        ch.setName(testCheese.getName());
        ch.setDescription(testCheese.getDescription());
        ch.setCategory(testCheese.getCategory());
        ch.setRating(testCheese.getRating());

        cheeseDao.save(ch);

        return "redirect:/cheese";
    }

    @RequestMapping(value = "category/{categoryId}", method = RequestMethod.GET)
    public String category(Model model, @PathVariable int categoryId){


        Iterable<Cheese> c = cheeseDao.findAll();
        ArrayList<Cheese> addTheseCheese = new ArrayList<>();
        for(Cheese chee : c)
        {
            if(categoryId == chee.getCategory().getId())
                addTheseCheese.add(chee);
        }

        model.addAttribute("cheeses", addTheseCheese );
        model.addAttribute("title", "Cheeses with Category: " + categoryDao.findById(categoryId).get().getName());
        return"cheese/index";
    }

}
