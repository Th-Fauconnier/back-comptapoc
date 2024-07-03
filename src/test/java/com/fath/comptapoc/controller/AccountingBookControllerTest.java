package com.fath.comptapoc.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AccountingBookController.class )
public class AccountingBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testBadRoute() throws Exception {
        mockMvc.perform( get("/badRoute") )
                .andExpect( status().isNotFound() );
    }

    @Test
    public void testGoodRoute() throws Exception {
        mockMvc.perform( get("/accountBook/test") )
                .andExpect( status().isOk() );
    }
}
