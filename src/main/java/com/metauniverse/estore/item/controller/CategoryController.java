package com.metauniverse.estore.item.controller;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.item.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/category")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private final ItemService itemService;
    private Map<Long, String> itemsImgLinks;

    @GetMapping("/all")
    public String getAllCategories(Model model) {
        Iterable<Item> items = itemService.getEveryItem();
        itemService.setImageLinksIntoSession(items);
        model.addAttribute("itemsImgLinks", itemsImgLinks);
        model.addAttribute("items", items);
        return "categories-all";
    }

    @GetMapping("/smartphones")
    public String getSmartphones(Model model) {
        Iterable<Item> smartphones = itemService.getItemsByType(ItemType.ANDROID_SMARTPHONE.getValue());
        itemService.setImageLinksIntoSession(smartphones);
        model.addAttribute("itemsImgLinks", itemsImgLinks);
        model.addAttribute("smartphones", smartphones);
        return "categories-smartphones";
    }

    @GetMapping("/laptops")
    public String getLaptops(Model model) {
        Iterable<Item> laptops = itemService.getItemsByType(ItemType.LAPTOP.getValue());
        itemService.setImageLinksIntoSession(laptops);
        model.addAttribute("itemsImgLinks", itemsImgLinks);
        model.addAttribute("laptops", laptops);
        return "categories-laptops";
    }

    @GetMapping("/desktop-pcs")
    public String getDesktopPCs(Model model) {
        Iterable<Item> pcs = itemService.getItemsByType(ItemType.DESKTOP_PC.getValue());
        itemService.setImageLinksIntoSession(pcs);
        model.addAttribute("itemsImgLinks", itemsImgLinks);
        model.addAttribute("pcs", pcs);
        return "categories-desktop-pcs";
    }

    @GetMapping("/pc-components")
    public String getPCComponents(Model model) {
        Iterable<Item> pcComponents = itemService.getItemsByType(ItemType.PC_COMPONENT.getValue());
        itemService.setImageLinksIntoSession(pcComponents);
        model.addAttribute("itemsImgLinks", itemsImgLinks);
        model.addAttribute("pcComponents", pcComponents);
        return "categories-pc-components";
    }

    @GetMapping("/consoles")
    public String getConsoles(Model model) {
        Iterable<Item> consoles = itemService.getItemsByType(ItemType.CONSOLE.getValue());
        itemService.setImageLinksIntoSession(consoles);
        model.addAttribute("itemsImgLinks", itemsImgLinks);
        model.addAttribute("consoles", consoles);
        return "categories-consoles";
    }

    /*public Map<Long, String> getItemsImgLinks(Iterable<Item> items, AmazonS3 s3client) {
        List<Item> itemList = new ArrayList<>();
        for (Item item : items) {
            itemList.add(item);
        }
        return bucketDataManager.getObjectsImageLinks(itemList, s3client, session);
    }

    public void setImageLinksIntoSession(Iterable<Item> items) {
        Map<Long, String> itemsImgLinks = (Map<Long, String>) session.getAttribute("itemsImgLinks");
        log.info("itemsImgLinks " + itemsImgLinks.toString());
        for (Item item : items) {
            if (!itemsImgLinks.containsKey(item.getId())) {
                AmazonS3 s3client = s3Initializer.init();
                itemsImgLinks = getItemsImgLinks(items, s3client);
                session.setAttribute("itemsImgLinks", itemsImgLinks);
            }
        }
    }*/
}
