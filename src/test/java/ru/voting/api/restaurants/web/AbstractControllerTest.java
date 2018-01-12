package ru.voting.api.restaurants.web;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.voting.api.restaurants.TestUtil;
import ru.voting.api.restaurants.model.BaseEntity;
import ru.voting.api.restaurants.model.User;
import ru.voting.api.restaurants.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.api.restaurants.TestData.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static ru.voting.api.restaurants.TestUtil.userAuth;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
abstract public class AbstractControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .apply(springSecurity())
                .build();
    }

    protected <T> void testGetEntities(String restUrl, User authUser, T... objects) throws Exception {
        mockMvc.perform(get(restUrl)
                .with(userAuth(authUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(objects.length == 1 ? objects[0] : objects));
    }

    protected <T extends BaseEntity> void testCreateEntity(String restUrl, User authUser, T created, Class<T> clazz) throws Exception {
        ResultActions action = mockMvc.perform(post(restUrl)
                .with(userAuth(authUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        T returned = TestUtil.readFromJson(action, clazz);
        created.setId(returned.getId());
        assertMatch(returned, created);
    }

    protected <T> void testUpdateEntity(String restUrl, User authUser, T updated) throws Exception {
        mockMvc.perform(put(restUrl)
                .with(userAuth(authUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
    }

    protected void testDeleteEntity(String restUrl, User authUser) throws Exception {
        mockMvc.perform(delete(restUrl)
                .with(userAuth(authUser)))
                .andExpect(status().isNoContent());
    }
}
