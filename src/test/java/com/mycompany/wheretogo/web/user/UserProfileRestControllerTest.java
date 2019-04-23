package com.mycompany.wheretogo.web.user;

import com.mycompany.wheretogo.model.User;
import com.mycompany.wheretogo.to.UserTo;
import com.mycompany.wheretogo.util.UserUtil;
import com.mycompany.wheretogo.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.mycompany.wheretogo.TestUtil.userHttpBasic;
import static com.mycompany.wheretogo.UserTestData.*;
import static com.mycompany.wheretogo.web.user.UserProfileRestController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserProfileRestControllerTest extends AbstractUserRestControllerTest {
    @Test
    public void testGetUnauthorized() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(fromJsonAndAssert(USER));
    }

    @Test
    public void testUpdate() throws Exception {
        UserTo updated = new UserTo(null, "Updated Name", "updated@mail.com", "updatedPassword");
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.get(USER_ID), UserUtil.updateFromTo(new User(USER), updated));
    }

    @Test
    public void testUpdateNotValid() throws Exception {
        UserTo updated = new UserTo(null, "", "updated@mail.com", "");
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN);
    }
}