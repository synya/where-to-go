package com.mycompany.wheretogo.web.json;

import com.mycompany.wheretogo.TestUtil;
import com.mycompany.wheretogo.to.RestaurantTo;
import org.junit.Test;

import java.util.List;

import static com.mycompany.wheretogo.MenuItemTestData.*;
import static com.mycompany.wheretogo.util.RestaurantUtil.groupMenuItemsByRestaurant;

public class JsonUtilTest {
    @Test
    public void testReadWriteValue() throws Exception {
        RestaurantTo toJson = groupMenuItemsByRestaurant(List.of(TODAY_MENU_ITEM2, TODAY_MENU_ITEM1), null).get(0);
        String json = JsonUtil.writeValue(toJson);
        System.out.println(json);
        RestaurantTo fromJson = JsonUtil.readValue(json, RestaurantTo.class);
        TestUtil.assertMatch(fromJson, toJson);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        List<RestaurantTo> toJsonList = groupMenuItemsByRestaurant(TODAY_MENU_ITEMS, null);
        String json = JsonUtil.writeValue(toJsonList);
        System.out.println(json);
        List<RestaurantTo> fromJsonList = JsonUtil.readValues(json, RestaurantTo.class);
        TestUtil.assertMatch(fromJsonList, toJsonList);
    }
}