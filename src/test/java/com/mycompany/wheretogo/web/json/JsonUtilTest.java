package com.mycompany.wheretogo.web.json;

import com.mycompany.wheretogo.TestUtil;
import com.mycompany.wheretogo.to.RestaurantsTo;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static com.mycompany.wheretogo.MenuItemTestData.*;
import static com.mycompany.wheretogo.util.MenuItemsUtil.asListOfRestaurantsTo;
import static com.mycompany.wheretogo.util.MenuItemsUtil.asRestaurantsToWithVote;

public class JsonUtilTest {
    @Test
    public void testReadWriteValue() throws Exception {
        RestaurantsTo toJson = asRestaurantsToWithVote(LocalDate.now(), List.of(TODAY_MENU_ITEM2, TODAY_MENU_ITEM1), null);
        String json = JsonUtil.writeValue(toJson);
        System.out.println(json);
        RestaurantsTo fromJson = JsonUtil.readValue(json, RestaurantsTo.class);
        TestUtil.assertMatch(fromJson, toJson);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        List<RestaurantsTo> toJsonList = asListOfRestaurantsTo(TODAY_MENU_ITEMS);
        String json = JsonUtil.writeValue(toJsonList);
        System.out.println(json);
        List<RestaurantsTo> fromJsonList = JsonUtil.readValues(json, RestaurantsTo.class);
        TestUtil.assertMatch(fromJsonList, toJsonList);
    }
}