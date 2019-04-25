package com.mycompany.wheretogo.web.restaurant;

import com.mycompany.wheretogo.TestUtil;
import com.mycompany.wheretogo.to.RestaurantsTo;
import com.mycompany.wheretogo.web.AbstractRestControllerTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static com.mycompany.wheretogo.MenuItemTestData.TODAY_MENU_ITEMS;
import static com.mycompany.wheretogo.util.MenuItemsUtil.asRestaurantsTo;
import static com.mycompany.wheretogo.web.restaurant.RestaurantRestController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantRestControllerTest extends AbstractRestControllerTest {
    @Test
    public void testGetRestaurants() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(asRestaurantsTo(LocalDate.now(), TODAY_MENU_ITEMS), RestaurantsTo.class));
    }
}