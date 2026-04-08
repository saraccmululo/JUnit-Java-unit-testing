package service;

import com.sara.unittestingpractice.entity.Pack;
import com.sara.unittestingpractice.repository.PackRepository;
import com.sara.unittestingpractice.service.PackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PackServiceTest {

  @Mock
  private PackRepository packRepository;

  @InjectMocks
  PackService packService;

  Pack pack;

  @BeforeEach
  void setUp(){
    pack = new Pack(1L,"IN_RETAILER", 100L);
  }

  // ---Activate pack / Happy Path ---
  @Test
  void activatedPack_shouldSetStatusToActivated() {
    when(packRepository.findById(pack.getPackId())).thenReturn(Optional.of(pack));
    when(packRepository.save(any(Pack.class))).thenReturn(pack);

    Pack result = packService.activatePackById(pack.getPackId());
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
    when(packRepository.findById(pack.getPackId())).thenReturn(Optional.of(pack));
    Exception exception = assertThrows(IllegalArgumentException.class, ()->packService.activatePackById(pack.getPackId()));
    assertEquals("Pack must be in retailer to be activated",
        exception.getMessage());
    verify(packRepository, never()).save(any());
  }

  // --- Pack Not Found ---
  @Test
  void activatePack_shouldThrowIfPackNotFound(){
    when(packRepository.findById(999L)).thenReturn(Optional.empty());
    Exception exception = assertThrows(java.util.NoSuchElementException.class,
        () -> packService.activatePackById(999L));
    assertEquals("Pack not found with id: 999", exception.getMessage());
  }

  //---Find Retailer Packs / Happy Path ---
  @Test
  void getPacksByRetailer_shouldReturnListOfPacks() {
    Long retailerId = 1L;
    //create a list of packs to be returned
    List<Pack> mockPacks = List.of(
        new Pack(1L, "IN_RETAILER", 32L),
        new Pack(2L, "SHIPPED", 28L),
        new Pack(3L, "ACTIVATED", 11L)
    );
    when(packRepository.findByRetailerId(retailerId)).thenReturn(mockPacks);
    List<Pack> result = packService.getPacksByRetailer(retailerId);
    assertEquals(3, result.size());
    assertEquals(mockPacks, result);
    //verify if packRepository.findByRetailerId() was called
    verify(packRepository).findByRetailerId(retailerId);
  }

  // ---Find Retailer Packs / Pack List not Found --

  @Test
  void getPacksByRetailer_shouldReturnEmptyListIfNoPacks() {
    when(packRepository.findByRetailerId(1L)).thenReturn(List.of());
    List<Pack> result = packService.getPacksByRetailer(1L);
    assertTrue(result.isEmpty());
    verify(packRepository).findByRetailerId(1L);
  }
}
