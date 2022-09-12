package com.example.utilityapi.controller;

import com.example.utilityapi.models.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    // Wiring in the MockMvc object
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    // Testing POST /records
    @Test
    public void shouldReturnNewRecordOnPostRequest() throws Exception {

        // ARRANGE
        Account inputAccount = new Account();
        inputAccount.setUsername("Rick Fox");
        inputAccount.setPassword("UNC_44");

        // Convert Java Object to JSON
        String inputJson = mapper.writeValueAsString(inputAccount);

        Account outputAccount = new Account();
        outputAccount.setUsername("Rick Fox");
        outputAccount.setPassword("UNC_44");
        outputAccount.setId(4);

        String outputJson = mapper.writeValueAsString(outputAccount);

        // ACT
        mockMvc.perform(
                        post("/account")                            // Perform the POST request
                                .content(inputJson)                       // Set the request body
                                .contentType(MediaType.APPLICATION_JSON)  // Tell the server it's in JSON format
                )
                .andDo(print())                                // Print results to console
                .andExpect(status().isCreated());              // ASSERT (status code is 201)
    }

    // Testing POST /records
    @Test
    public void shouldHandleExcessLengthPassword() throws Exception {

        // ARRANGE
        Account inputAccount = new Account();
        inputAccount.setUsername("Tyler Zeller");
        inputAccount.setPassword("Way_Too_Long_Password_44");

        // Convert Java Object to JSON
        String inputJson = mapper.writeValueAsString(inputAccount);

        // ACT
        mockMvc.perform(
                        post("/account")                            // Perform the POST request
                                .content(inputJson)                       // Set the request body
                                .contentType(MediaType.APPLICATION_JSON)  // Tell the server it's in JSON format
                )
                .andDo(print())                                // Print results to console
                .andExpect(status().is4xxClientError());
    }

    // Testing POST /records
    @Test
    public void shouldHandleDuplicateUsername() throws Exception {

        // ARRANGE
        Account inputAccount = new Account();
        inputAccount.setUsername("Danny Ainge");
        inputAccount.setPassword("Password_44");

        // Convert Java Object to JSON
        String inputJson = mapper.writeValueAsString(inputAccount);

        // ACT
        mockMvc.perform(
                        post("/account")                            // Perform the POST request
                                .content(inputJson)                       // Set the request body
                                .contentType(MediaType.APPLICATION_JSON)  // Tell the server it's in JSON format
                )
                .andDo(print())                                // Print results to console
                .andExpect(status().is4xxClientError());
    }
}
