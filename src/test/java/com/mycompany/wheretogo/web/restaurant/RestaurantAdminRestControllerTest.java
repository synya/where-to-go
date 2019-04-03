package com.mycompany.wheretogo.web.restaurant;

import com.mycompany.wheretogo.TestUtil;
import com.mycompany.wheretogo.model.Restaurant;
import com.mycompany.wheretogo.service.RestaurantService;
import com.mycompany.wheretogo.web.AbstractRestControllerTest;
import com.mycompany.wheretogo.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.mycompany.wheretogo.RestaurantTestData.*;
import static com.mycompany.wheretogo.web.restaurant.RestaurantAdminRestController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantAdminRestControllerTest extends AbstractRestControllerTest {
    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testGetRestaurants() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(List.of(BURGER_KING, RESTAURANT_ATEOTU), Restaurant.class));
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
    public void testUpdateRestaurant() throws Exception {
        Restaurant updatedRestaurant = new Restaurant(BURGER_KING_ID, "Rebranded Burger King");
        mockMvc.perform(put(REST_URL + '/' + BURGER_KING_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurant)))
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.get(BURGER_KING_ID), updatedRestaurant);
    }

    @Test
    public void testDeleteRestaurant() throws Exception {
        mockMvc.perform(delete(REST_URL + '/' + BURGER_KING_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(restaurantService.getAll(), List.of(RESTAURANT_ATEOTU));
    }
}