package controller;

import com.sara.unittestingpractice.controller.PackController;
import com.sara.unittestingpractice.entity.Pack;
import com.sara.unittestingpractice.service.PackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PackController.class)
public class PackControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PackService packService;

  // SUCCESS CASE
  @Test
  void activatePack_shouldReturn200_whenSuccess() throws Exception {

    Pack pack = new Pack(1L, "ACTIVATED", 100L);

    when(packService.activatePackById(1L)).thenReturn(pack);

    mockMvc.perform(post("/packs/1/activate"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("ACTIVATED"));
  }

  // NOT FOUND (404)
  @Test
  void activatePack_shouldReturn404_whenPackNotFound() throws Exception {

    when(packService.activatePackById(1L))
        .thenThrow(new NoSuchElementException("Pack not found with id: 1"));

    mockMvc.perform(post("/packs/1/activate"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Pack not found with id: 1"));
  }

  // INVALID STATUS (400)
  @Test
  void activatePack_shouldReturn400_whenInvalidStatus() throws Exception {

    when(packService.activatePackById(1L))
        .thenThrow(new IllegalArgumentException("Pack must be in retailer to be activated"));

    mockMvc.perform(post("/packs/1/activate"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("Pack must be in retailer to be activated"));
  }
}