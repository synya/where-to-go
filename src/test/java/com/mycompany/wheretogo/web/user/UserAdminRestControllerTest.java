package com.mycompany.wheretogo.web.user;

import com.mycompany.wheretogo.model.Role;
import com.mycompany.wheretogo.model.User;
import com.mycompany.wheretogo.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.mycompany.wheretogo.TestUtil.*;
import static com.mycompany.wheretogo.UserTestData.*;
import static com.mycompany.wheretogo.web.user.UserAdminRestController.REST_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAdminRestControllerTest extends AbstractUserRestControllerTest {
    @Test
    public void testGetUnauthorized() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(fromJsonAndAssert(USER));
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "/by?email=" + USER.getEmail())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(fromJsonAndAssert(USER));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + ENTITY_NOT_FOUND_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(fromJsonAndAssert(ADMIN, USER));
    }

    @Test
    public void testGetAllForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAdd() throws Exception {
        User expected = new User(null, "New User", "newuser@gmail.com", "password", Role.ROLE_USER);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonWithPassword(expected, "password"))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isCreated());
        User returned = readFromJson(action, User.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN, expected, USER);
    }

    @Test
    public void testAddNotValid() throws Exception {
        User expected = new User(null, "", "newuser@gmail.com", "", Role.ROLE_USER);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testAddDuplicateEmail() throws Exception {
        User expected = new User(null, "New User", USER.getEmail(), "password", Role.ROLE_USER);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(toJsonWithPassword(expected, "password")))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("Updated Name");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + "/" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonWithPassword(updated, USER.getPassword()))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.get(USER_ID), updated);
    }

    @Test
    public void testUpdateNotValid() throws Exception {
        User updated = new User(USER);
        updated.setName("");
        mockMvc.perform(put(REST_URL + "/" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), List.of(ADMIN));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + ENTITY_NOT_FOUND_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}