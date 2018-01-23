package ru.voting.api.restaurants.web;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
abstract public class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Before
    @CacheEvict(value = {"restaurants", "users"}, allEntries = true)
    public void setUp() throws Exception {
        //clear cache
    }

}
