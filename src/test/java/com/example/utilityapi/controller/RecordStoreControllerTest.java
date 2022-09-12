package com.example.utilityapi.controller;

import com.example.utilityapi.models.Record;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(RecordStoreController.class)
public class RecordStoreControllerTest {

    // Wiring in the MockMvc object
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    // A list of records for testing purposes
    private List<Record> recordList;

    @Before
    public void setUp() {
        // Standard set up method, for instantiating test objects
        // Don't have to do anything special for mockMvc since it's Autowired
    }

    // Testing GET /records
    @Test
    public void shouldReturnAllRecordsInCollection() throws Exception {

        // ARRANGE
        // Convert Java object to JSON
        String outputJson = mapper.writeValueAsString(recordList);

        // ACT
        mockMvc.perform(get("/records"))                // Perform the GET request
                .andDo(print())                          // Print results to console
                .andExpect(status().isOk());              // ASSERT (status code is 200)
    }

    // Testing GET record/{id}
    @Test
    public void shouldReturnRecordById() throws Exception {

        Record outputRecord = new Record();
        outputRecord.setArtist("The Beach Boys");
        outputRecord.setAlbum("Pet Sounds");
        outputRecord.setYear("1968");
        outputRecord.setId(1);

        String outputJson = mapper.writeValueAsString(outputRecord);

        mockMvc.perform(get("/records/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    // Testing GET record/{id}
    @Test
    public void shouldHandleInvalidId() throws Exception {

        mockMvc.perform(get("/records/999999"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
