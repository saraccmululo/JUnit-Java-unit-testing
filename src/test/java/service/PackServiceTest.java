package service;

import com.sara.unittestingpractice.entity.Pack;
import com.sara.unittestingpractice.repository.PackRepository;
import com.sara.unittestingpractice.service.PackService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

public class PackServiceTest {

  @Mock
  private PackRepository packRepository;

  @InjectMocks
  PackService packService;

  Pack pack;
  private AutoCloseable mocks;


  @BeforeEach
  void setUp(){
    mocks=MockitoAnnotations.openMocks(this);
    pack = new Pack();
    pack.setStatus("IN_RETAILER");
    pack.setId(1L);
  }

  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  // --- Happy Path ---
  @Test
  void activatedPack_shouldSetStatusToActivated() {
    when(packRepository.findById(pack.getId())).thenReturn(Optional.of(pack));
    when(packRepository.save(any(Pack.class))).thenReturn(pack);

    Pack result = packService.activatePackById(pack.getId());
    assertEquals("ACTIVATED", result.getStatus());
    verify(packRepository).save(pack);
  }

  // --- Invalid Statuses ---
  @ParameterizedTest
  @CsvSource({
      "IN_WAREHOUSE",
      "SHIPPED",
      "CREATED"
  })

  void activatePack_shouldThrowForInvalidStatuses(String status) {
    pack.setStatus(status);
    when(packRepository.findById(pack.getId())).thenReturn(Optional.of(pack));
    Exception exception = assertThrows(IllegalArgumentException.class, ()->packService.activatePackById(pack.getId()));
    assertEquals("com.sara.unittestingpractice.entity.Pack must be in retailer to be activated",
        exception.getMessage());
    verify(packRepository, never()).save(any());
  }

  // --- com.sara.unittestingpractice.entity.Pack Not Found ---
  @Test
  void activatePack_shouldThrowIfPackNotFound(){
    when(packRepository.findById(999L)).thenReturn(Optional.empty());
    Exception exception = assertThrows(java.util.NoSuchElementException.class,
        () -> packService.activatePackById(999L));
    assertEquals("com.sara.unittestingpractice.entity.Pack not found with id: 999", exception.getMessage());
  }
}
