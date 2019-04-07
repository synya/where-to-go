package com.mycompany.wheretogo.web.restaurant;

import com.mycompany.wheretogo.TestUtil;
import com.mycompany.wheretogo.model.Dish;
import com.mycompany.wheretogo.model.MenuItem;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.service.RestaurantService;
import com.mycompany.wheretogo.to.RestaurantsTo;
import com.mycompany.wheretogo.util.JpaUtil;
import com.mycompany.wheretogo.web.AbstractRestControllerTest;
import com.mycompany.wheretogo.web.json.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static com.mycompany.wheretogo.DishTestData.*;
import static com.mycompany.wheretogo.MenuItemTestData.*;
import static com.mycompany.wheretogo.RestaurantTestData.*;
import static com.mycompany.wheretogo.util.RestaurantUtil.groupByDateAndRestaurant;
import static com.mycompany.wheretogo.web.restaurant.RestaurantAdminRestController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantAdminRestControllerTest extends AbstractRestControllerTest {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("todayMenuItems").clear();
        cacheManager.getCache("menuItems").clear();
        cacheManager.getCache("dishes").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testGetRestaurants() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(List.of(BURGER_KING, RESTAURANT_ATEOTU), Restaurant.class));
    }

    @Test
    public void testGetRestaurantDishes() throws Exception {
        mockMvc.perform(get(REST_URL + '/' + RESTAURANT_ATEOTU_ID + "/dishes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(List.of(RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                        RESTAURANT_ATEOTU_DISH1, RESTAURANT_ATEOTU_DISH5, RESTAURANT_ATEOTU_DISH2), Dish.class));
    }

    @Test
    public void testGetTodayMenuItems() throws Exception {
        mockMvc.perform(get(REST_URL + "/menus/daily/today/items"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(TODAY_MENU_ITEMS, MenuItem.class));
    }

    @Test
    public void testGetAllRestaurantsMenus() throws Exception {
        mockMvc.perform(get(REST_URL + "/menus/daily"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(groupByDateAndRestaurant(ALL_MENU_ITEMS), RestaurantsTo.class));
    }

    @Test
    public void testGetAllRestaurantsMenusBetweenDates() throws Exception {
        mockMvc.perform(get(REST_URL + "/menus/daily/between?startDate=2019-03-20&endDate=2019-03-21"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(groupByDateAndRestaurant(HISTORY_MENU_ITEMS), RestaurantsTo.class));
    }

    @Test
    public void testAddRestaurant() throws Exception {
        Restaurant expectedRestaurant = new Restaurant("The New Place To Have Launch At");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expectedRestaurant)))
                .andDo(print())
                .andExpect(status().isCreated());
        Restaurant returnedRestaurant = TestUtil.readFromJson(action, Restaurant.class);
        expectedRestaurant.setId(returnedRestaurant.getId());
        assertMatch(returnedRestaurant, expectedRestaurant);
        assertMatch(restaurantService.getAll(), BURGER_KING, returnedRestaurant, RESTAURANT_ATEOTU);
    }

    @Test
    public void testAddRestaurantDish() throws Exception {
        Dish expectedDish = new Dish("The New And Delicious Dish", RESTAURANT_ATEOTU);
        ResultActions action = mockMvc.perform(post(REST_URL + '/' + RESTAURANT_ATEOTU_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expectedDish)))
                .andDo(print())
                .andExpect(status().isCreated());
        Dish returnedDish = TestUtil.readFromJson(action, Dish.class);
        expectedDish.setId(returnedDish.getId());
        assertMatch(returnedDish, expectedDish);
        assertMatch(restaurantService.getAllDishes(RESTAURANT_ATEOTU_ID), RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                RESTAURANT_ATEOTU_DISH1, RESTAURANT_ATEOTU_DISH5, expectedDish, RESTAURANT_ATEOTU_DISH2);
    }

    @Test
    public void testAddTodayMenuItem() throws Exception {
        MenuItem expectedMenuItem = new MenuItem(RESTAURANT_ATEOTU_DISH3, LocalDate.now(), 10020);
        ResultActions action = mockMvc.perform(post(REST_URL + "/menus/daily/today/items?dishId=100012&price=10020"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        MenuItem returnedMenuItem = TestUtil.readFromJson(action, MenuItem.class);
        expectedMenuItem.setId(returnedMenuItem.getId());
        assertMatch(returnedMenuItem, expectedMenuItem);
        assertMatch(restaurantService.getAllTodayMenuItems(), TODAY_MENU_ITEM2, TODAY_MENU_ITEM1, TODAY_MENU_ITEM6, expectedMenuItem,
                TODAY_MENU_ITEM4, TODAY_MENU_ITEM3, TODAY_MENU_ITEM5);
    }

    @Test
    public void testUpdateRestaurant() throws Exception {
        Restaurant updatedRestaurant = new Restaurant(BURGER_KING_ID, "Rebranded Burger King");
        Integer updatedRestaurantId = BURGER_KING_ID;
        mockMvc.perform(put(REST_URL + '/' + updatedRestaurantId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurant)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.get(updatedRestaurantId), updatedRestaurant);
    }

    @Test
    public void testUpdateRestaurantDish() throws Exception {
        Dish updatedDish = new Dish("The Pan Galactic Gargle Blaster Updated");
        Integer updatedDishId = RESTAURANT_ATEOTU_DISH2.getId();
        mockMvc.perform(put(REST_URL + '/' + RESTAURANT_ATEOTU_ID + "/dishes/" + updatedDishId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedDish)))
                .andDo(print())
                .andExpect(status().isNoContent());
        updatedDish.setId(updatedDishId);
        updatedDish.setRestaurant(RESTAURANT_ATEOTU);
        assertMatch(restaurantService.getDish(updatedDishId), updatedDish);
    }

    @Test
    public void testUpdateTodayMenuItem() throws Exception {
        Integer updatedMenuItemId = TODAY_MENU_ITEM3.getId();
        MenuItem updatedMenuItem = new MenuItem(updatedMenuItemId, RESTAURANT_ATEOTU_DISH1, LocalDate.now(), 2560);
        mockMvc.perform(put(REST_URL + "/menus/daily/today/items/" + updatedMenuItemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedMenuItem)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAllTodayMenuItems(), TODAY_MENU_ITEM2, TODAY_MENU_ITEM1, TODAY_MENU_ITEM6,
                TODAY_MENU_ITEM4, updatedMenuItem, TODAY_MENU_ITEM5);
    }

    @Test
    public void testDeleteRestaurant() throws Exception {
        mockMvc.perform(delete(REST_URL + '/' + BURGER_KING_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAll(), List.of(RESTAURANT_ATEOTU));
    }

    @Test
    public void testDeleteRestaurantDish() throws Exception {
        mockMvc.perform(delete(REST_URL + '/' + RESTAURANT_ATEOTU_ID + "/dishes/" + RESTAURANT_ATEOTU_DISH1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAllDishes(RESTAURANT_ATEOTU_ID), RESTAURANT_ATEOTU_DISH4, RESTAURANT_ATEOTU_DISH3, RESTAURANT_ATEOTU_DISH6,
                RESTAURANT_ATEOTU_DISH5, RESTAURANT_ATEOTU_DISH2);

    }

    @Test
    public void testDeleteTodayMenuItem() throws Exception {
        mockMvc.perform(delete(REST_URL + "/menus/daily/today/items/" + TODAY_MENU_ITEM3.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAllTodayMenuItems(), TODAY_MENU_ITEM2, TODAY_MENU_ITEM1, TODAY_MENU_ITEM6,
                TODAY_MENU_ITEM4, TODAY_MENU_ITEM5);

    }
}