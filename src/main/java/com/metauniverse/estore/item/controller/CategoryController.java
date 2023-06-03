package com.metauniverse.estore.item.controller;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final ItemRepository itemRepository;

    @GetMapping("/all")
    public String getAllCategories(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "categories-all";
    }

    @GetMapping("/smartphones")
    public String getSmartphones() {
        return "categories-smartphones";
    }

    @GetMapping("/laptops")
    public String getLaptops() {
        return "categories-laptops";
    }

    @GetMapping("/desktop-pcs")
    public String getDesktopPCs() {
        return "categories-desktop-pcs";
    }

    @GetMapping("/pc-components")
    public String getPCComponents() {
        return "categories-pc-components";
    }

    @GetMapping("/consoles")
    public String getConsoles() {
        return "categories-consoles";
    }
}
