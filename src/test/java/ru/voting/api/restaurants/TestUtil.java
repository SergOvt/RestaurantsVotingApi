package ru.voting.api.restaurants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.voting.api.restaurants.model.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class TestUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static RequestPostProcessor userAuth(User user) {
        return SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(user.getEmail(),
                user.getPassword().replace("{noop}", "")));
    }

    public static <T> String writeValue(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static <T> T readValue(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        String json = action.andReturn().getResponse().getContentAsString();
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> ResultMatcher contentJson(T expected) {
        return content().json(writeProps(expected));
    }

    @SafeVarargs
    public static <T> ResultMatcher contentJson(T... expected) {
        return content().json(writeProps(Arrays.asList(expected)));
    }

    public static <T> String writeProps(Collection<T> collection) {
        List<Map<String, Object>> list = collection.stream()
                .map(obj -> getAsMap(obj))
                .collect(Collectors.toList());
        return writeValue(list);
    }

    public static <T> String writeProps(T obj) {
        Map<String, Object> map = getAsMap(obj);
        return writeValue(map);
    }

    private static <T> Map<String, Object> getAsMap(T obj) {
        return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {
        });
    }
}
