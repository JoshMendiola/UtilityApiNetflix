package com.example.utilityapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(CalculatorController.class)
public class CalculatorControllerTest {

    // Wiring in the MockMvc object
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    // Testing GET calculator/divide/?value1={value1}&value2={value2}
    @Test
    public void shouldDivideNonZero() throws Exception {

        double expectedResult = 5;
        String outputJson = mapper.writeValueAsString(expectedResult);

        mockMvc.perform(get("/calculator/divide/?value1=125&value2=25"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    // Testing GET calculator/divide/?value1={value1}&value2={value2}
    @Test
    public void shouldHandleDivideByZero() throws Exception {

        mockMvc.perform(get("/calculator/divide/?value1=1&value2=0"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    // Testing GET calculator/square/{id}
    @Test
    public void shouldSquareNonZero() throws Exception {

        int expectedResult = 25 * 25;
        String outputJson = mapper.writeValueAsString(expectedResult);

        mockMvc.perform(get("/calculator/square/25"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(outputJson));
    }

    // Testing GET calculator/square/{id}
    @Test
    public void shouldHandleLargeInput() throws Exception {

        mockMvc.perform(get("/calculator/square/1000000"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
