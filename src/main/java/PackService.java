public class PackService {
  private final PackRepository packRepository;
  public PackService(PackRepository packRepository) {
    this.packRepository = packRepository;
  }

  public Pack activatePack(Pack pack) {
    if (pack == null) {
      throw new IllegalArgumentException("Pack cannot be null"); // check if null
    }
    if(!"IN_RETAILER".equals(pack.getStatus())){ //check if status is not "IN_RETAILER"
      throw new IllegalArgumentException("Pack must be in retailer to be activated");
    }

    pack.setStatus("ACTIVATED"); //change status to activated
    return packRepository.save(pack);} //saving using the repository and returning pack
}
