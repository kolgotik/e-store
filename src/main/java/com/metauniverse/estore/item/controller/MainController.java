package com.metauniverse.estore.item.controller;

import com.metauniverse.estore.util.cart_util.SessionCartInitializer;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {

    private final SessionCartInitializer cartInitializer;
    @GetMapping
    public String mainPage(HttpSession session) {
        cartInitializer.initSessionCart(session);

        Map<Long, String> itemsImgLinks;
        Object attribute = session.getAttribute("itemsImgLinks");
        if (attribute instanceof Map) {
            itemsImgLinks = (Map<Long, String>) attribute;
            /*itemsImgLinks = new HashMap<>();
            session.setAttribute("itemsImgLinks", itemsImgLinks);*/
        } else {
            itemsImgLinks = new HashMap<>();
            session.setAttribute("itemsImgLinks", itemsImgLinks);
        }

        return "index";
    }


}
