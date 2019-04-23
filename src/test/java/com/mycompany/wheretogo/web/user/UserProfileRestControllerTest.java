package com.mycompany.wheretogo.web.user;

import com.mycompany.wheretogo.TestUtil;
import com.mycompany.wheretogo.to.UserTo;
import com.mycompany.wheretogo.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static com.mycompany.wheretogo.TestUtil.userHttpBasic;
import static com.mycompany.wheretogo.UserTestData.*;
import static com.mycompany.wheretogo.util.UserUtil.asTo;
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
        UserTo expected = asTo(USER);
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.fromJsonAndAssert(expected, UserTo.class, "password"));
    }

    @Test
    public void testUpdate() throws Exception {
        UserTo updated = asTo(USER);
        updated.setName("Updated Name");
        updated.setEmail("updated@mail.com");
        updated.setPassword("updatedPassword");
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeAdditionProps(updated, "password", "updatedPassword"))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        TestUtil.assertMatch(asTo(userService.get(USER_ID)), updated, "password");
    }

    @Test
    public void testUpdateNotValid() throws Exception {
        UserTo updated = asTo(USER);
        updated.setName("");
        updated.setEmail("updated@mail.com");
        updated.setPassword("");
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeAdditionProps(updated, "password", ""))
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