package controller;

import com.sara.unittestingpractice.controller.PackController;
import com.sara.unittestingpractice.entity.Pack;
import com.sara.unittestingpractice.service.PackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
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

    Pack pack = new Pack();
    pack.setStatus("ACTIVATED");

    when(packService.activatePackById(1L)).thenReturn(pack);

    mockMvc.perform(post("/packs/1/activate"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("ACTIVATED"));
  }

  // NOT FOUND (404)
  @Test
  void activatePack_shouldReturn404_whenPackNotFound() throws Exception {

    when(packService.activatePackById(anyLong()))
        .thenThrow(new java.util.NoSuchElementException("com.sara.unittestingpractice.entity.Pack not found with id: 1"));

    mockMvc.perform(post("/packs/1/activate"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("com.sara.unittestingpractice.entity.Pack not found with id: 1"));
  }

  // INVALID STATUS (400)
  @Test
  void activatePack_shouldReturn400_whenInvalidStatus() throws Exception {

    when(packService.activatePackById(anyLong()))
        .thenThrow(new IllegalArgumentException("com.sara.unittestingpractice.entity.Pack must be in retailer to be activated"));

    mockMvc.perform(post("/packs/1/activate"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("com.sara.unittestingpractice.entity.Pack must be in retailer to be activated"));
  }
}