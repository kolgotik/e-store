package com.metauniverse.estore.item.controller;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.item.ItemType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final ItemService itemService;

    @GetMapping("/test")
    public String testCategory(){
        return "categories";
    }
    @GetMapping("/all")
    public String getAllCategories(Model model) {
        Iterable<Item> items = itemService.getEveryItem();
        model.addAttribute("items", items);
        return "categories-all";
    }

    @GetMapping("/smartphones")
    public String getSmartphones(Model model) {
        Iterable<Item> smartphones = itemService.getItemsByType(ItemType.ANDROID_SMARTPHONE.getValue());
        model.addAttribute("smartphones", smartphones);
        return "categories-smartphones";
    }

    @GetMapping("/laptops")
    public String getLaptops(Model model) {
        Iterable<Item> laptops = itemService.getItemsByType(ItemType.LAPTOP.getValue());
        model.addAttribute("laptops", laptops);
        return "categories-laptops";
    }

    @GetMapping("/desktop-pcs")
    public String getDesktopPCs(Model model) {
        Iterable<Item> pcs = itemService.getItemsByType(ItemType.DESKTOP_PC.getValue());
        model.addAttribute("pcs", pcs);
        return "categories-desktop-pcs";
    }

    @GetMapping("/pc-components")
    public String getPCComponents(Model model) {
        Iterable<Item> pcComponents = itemService.getItemsByType(ItemType.PC_COMPONENT.getValue());
        model.addAttribute("pcComponents", pcComponents);
        return "categories-pc-components";
    }

    @GetMapping("/consoles")
    public String getConsoles(Model model) {
        Iterable<Item> consoles = itemService.getItemsByType(ItemType.CONSOLE.getValue());
        model.addAttribute("consoles", consoles);
        return "categories-consoles";
    }
}
