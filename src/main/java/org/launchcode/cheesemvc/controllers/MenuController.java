package org.launchcode.cheesemvc.controllers;

import org.launchcode.cheesemvc.models.Cheese;
import org.launchcode.cheesemvc.models.Menu;
import org.launchcode.cheesemvc.models.data.CheeseDao;
import org.launchcode.cheesemvc.models.data.MenuDao;
import org.launchcode.cheesemvc.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model){

        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Menus:");

        return "menu/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model){

        model.addAttribute("title", "Add Menu");
        model.addAttribute("menu", new Menu());

        return "menu/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute @Valid Menu menu, Errors errors, Model model){

        if(errors.hasErrors())
        {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        menuDao.save(menu);

        return "redirect:/menu/view/" + menu.getId();
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int id){

        Optional<Menu> m = menuDao.findById(id);
        Menu menu = m.get();
        model.addAttribute("title", menu.getName());
        model.addAttribute("menu", menu);

        return "menu/view";
    }

    @RequestMapping(value = "/add-item/{id}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int id){


        Optional<Menu> m = menuDao.findById(id);
        Menu menu = m.get();

        AddMenuItemForm form = new AddMenuItemForm(menu,cheeseDao.findAll());

        model.addAttribute("title", "Add item to menu: " + menu.getName());
        model.addAttribute("form", form);

        return "menu/add-item";
    }

    @RequestMapping(value = "/add-item", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid AddMenuItemForm form, Errors errors,
                          Model model){

       if(errors.hasErrors())
       {
           model.addAttribute("form", form);
           return"menu/add-item";
       }

       Optional<Cheese> c = cheeseDao.findById(form.getCheeseId());
       Cheese cheese = c.get();
       Optional<Menu> m = menuDao.findById(form.getMenuId());
       Menu menu = m.get();
       menu.addItem(cheese);

       menuDao.save(menu);

        return "redirect:/menu/view/" + menu.getId();
    }




}
