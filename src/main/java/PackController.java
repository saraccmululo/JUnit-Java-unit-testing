import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/packs")
public class PackController {

  private final PackService packService;
  private final PackRepository packRepository;

  public PackController(PackService packService, PackRepository packRepository) {
    this.packService = packService;
    this.packRepository = packRepository;
  }

  @PostMapping("/{id}/activate")
  public Pack activatePack(@PathVariable Long id) {

    // Step 1: Get the Pack from the repository
    Pack pack = packRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Pack not found with id: " + id));

    // Step 2: Pass the full Pack object to the service
    return packService.activatePack(pack);
  }
}