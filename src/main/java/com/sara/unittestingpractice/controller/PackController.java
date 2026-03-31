package com.sara.unittestingpractice.controller;

import com.sara.unittestingpractice.dto.PackDTO;
import com.sara.unittestingpractice.entity.Pack;
import com.sara.unittestingpractice.service.PackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/packs")
public class PackController {

  private final PackService packService;

  public PackController(PackService packService) {
    this.packService = packService;
  }

  // GET API: List all packs for retailer
  @GetMapping("retailer/{retailerId}")
  public List<PackDTO> getRetailerPacks(@PathVariable Long retailerId){
    List<Pack> packs = packService.getPacksByRetailer(retailerId);
    //return only safe fields
    return packs.stream()
        .map(pack -> new PackDTO(pack.getPackId(), pack.getGameId(), pack.getStatus()))
        .toList();
  }

  // POST API: Activate a pack
  @PostMapping("/{packId}/activate")
  public ResponseEntity<?> activatePack(@PathVariable Long packId) {
      Pack pack = packService.activatePackById(packId);

      PackDTO dto = new PackDTO(
          pack.getPackId(),
          pack.getGameId(),
          pack.getStatus()
      );
      return ResponseEntity.ok(dto);
    }
}

