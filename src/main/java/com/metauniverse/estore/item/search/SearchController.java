package com.metauniverse.estore.item.search;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
import com.metauniverse.estore.item.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private Map<Long, String> itemsImgLinks;
    @GetMapping("/item")
    public String searchForItem(@RequestParam("keyword") String keyword, Model model) {
        Iterable<Item> itemsFetchedByKeyword = itemRepository.getItemsByKeyword(keyword);
        itemService.setImageLinksIntoSession(itemsFetchedByKeyword);
        model.addAttribute("itemsImgLinks", itemsImgLinks);
        model.addAttribute("items", itemsFetchedByKeyword);
        return "categories-all";
    }

}
