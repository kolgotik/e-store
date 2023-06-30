package com.metauniverse.estore.item;

import com.amazonaws.services.s3.AmazonS3;
import com.metauniverse.estore.item.enums.ItemAvailability;
import com.metauniverse.estore.util.s3_util.util.AmazonS3Initializer;
import com.metauniverse.estore.util.s3_util.util.S3BucketDataManager;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private HttpSession session;
    private S3BucketDataManager bucketDataManager;
    private AmazonS3Initializer s3Initializer;
    public Iterable<Item> getItemsByType(String itemType) {
        return itemRepository.getItemsByType(itemType);
    }
    public Iterable<Item> getEveryItem() {
        return itemRepository.findAll();
    }
    public Integer getQuantityOfItem(Long id) {
        return itemRepository.getQuantityOfItem(id);
    }
    public Boolean isItemAvailable(Long id) {
        Integer quantity = itemRepository.getQuantityOfItem(id);
        if (quantity == null || quantity == 0) {
            return false;
        }
        return true;
    }
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }
    public void defineItemAvailability(Long id, Model model) {
        Boolean isAvailable = isItemAvailable(id);
        Integer itemQuantity = getQuantityOfItem(id);
        if (isAvailable) {
            model.addAttribute("availability", ItemAvailability.ITEM_AVAILABLE.getValue());
            if (itemQuantity <= 5) {
                model.addAttribute("availability", String.format(ItemAvailability.FEW_ITEMS_LEFT.getValue(), itemQuantity));
                model.addAttribute("fewLeftMSG", String.format(ItemAvailability.FEW_ITEMS_LEFT.getValue(), itemQuantity));
                System.out.println(String.format(ItemAvailability.FEW_ITEMS_LEFT.getValue(), itemQuantity));
            }
        } else {
            model.addAttribute("availability", ItemAvailability.ITEM_UNAVAILABLE.getValue());
        }
    }
    public Map<Long, String> getItemsImgLinks(Iterable<Item> items, AmazonS3 s3client) {
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
    }
}
