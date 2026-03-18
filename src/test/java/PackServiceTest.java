
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
  }
  @AfterEach
  void tearDown() throws Exception {
    mocks.close();
  }

  @Test
  void activatedPack_shouldSetStatusToActivated() {
    when(packRepository.save(any(Pack.class))).thenReturn(pack);

    Pack result = packService.activatePack(pack);
    assertEquals("ACTIVATED", result.getStatus());
  }

  @Test
  void activatePack_shouldCallRepositorySave() {
    when(packRepository.save(any(Pack.class))).thenReturn(pack);
    packService.activatePack(pack);
    verify(packRepository).save(pack);
  }

  @Test
  void activatePack_shouldReturnSavedPack() {
    when(packRepository.save(pack)).thenReturn(pack);
    Pack result=packService.activatePack(pack);
    assertNotNull(result);
    assertEquals(pack, result);
  }

  @ParameterizedTest
  @CsvSource({
      "IN_WAREHOUSE",
      "SHIPPED",
      "CREATED"
  })

  void activatePack_shouldThrowForInvalidStatuses(String status) {
    pack.setStatus(status);
    Exception exception = assertThrows(IllegalArgumentException.class, ()->packService.activatePack(pack));
    assertEquals("Pack must be in retailer to be activated",
        exception.getMessage());
    verify(packRepository, never()).save(any());
  }

  @Test
  void activatePack_shouldThrowExceptionWhenPackIsNull(){
    Exception exception = assertThrows(IllegalArgumentException.class, () -> packService.activatePack(null));
    assertEquals("Pack cannot be null", exception.getMessage());
  }
}
