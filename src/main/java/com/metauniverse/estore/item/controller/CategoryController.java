package com.metauniverse.estore.item.controller;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
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

    private final ItemRepository itemRepository;

    @GetMapping("/all")
    public String getAllCategories(Model model) {
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "categories-all";
    }

    @GetMapping("/smartphones")
    public String getSmartphones(Model model) {
        Iterable<Item> smartphones = itemRepository.getItemsByType(ItemType.ANDROID_SMARTPHONE.getValue());
        model.addAttribute("smartphones", smartphones);
        return "categories-smartphones";
    }

    @GetMapping("/laptops")
    public String getLaptops(Model model) {
        Iterable<Item> laptops = itemRepository.getItemsByType(ItemType.LAPTOP.getValue());
        model.addAttribute("laptops", laptops);
        return "categories-laptops";
    }

    @GetMapping("/desktop-pcs")
    public String getDesktopPCs(Model model) {
        Iterable<Item> pcs = itemRepository.getItemsByType(ItemType.DESKTOP_PC.getValue());
        model.addAttribute("pcs", pcs);
        return "categories-desktop-pcs";
    }

    @GetMapping("/pc-components")
    public String getPCComponents(Model model) {
        Iterable<Item> pcComponents = itemRepository.getItemsByType(ItemType.PC_COMPONENT.getValue());
        model.addAttribute("pcComponents", pcComponents);
        return "categories-pc-components";
    }

    @GetMapping("/consoles")
    public String getConsoles(Model model) {
        Iterable<Item> consoles = itemRepository.getItemsByType(ItemType.CONSOLE.getValue());
        model.addAttribute("consoles", consoles);
        return "categories-consoles";
    }
}
