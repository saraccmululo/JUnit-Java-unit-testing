import java.util.NoSuchElementException;

public class PackService {
  private final PackRepository packRepository;
  public PackService(PackRepository packRepository) {
    this.packRepository = packRepository;
  }

  public Pack activatePackById(Long id) {

    Pack pack = packRepository.findById(id)
        .orElseThrow(()-> new NoSuchElementException("Pack not found with id: " +id));

    if(!"IN_RETAILER".equals(pack.getStatus())){ //check if status is not "IN_RETAILER"
      throw new IllegalArgumentException("Pack must be in retailer to be activated");
    }

    pack.setStatus("ACTIVATED"); //change status to activated
    return packRepository.save(pack);} //saving using the repository and returning pack
}
