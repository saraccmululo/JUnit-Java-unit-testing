package com.sara.unittestingpractice.controller;

import com.sara.unittestingpractice.entity.Pack;
import com.sara.unittestingpractice.service.PackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/packs")
public class PackController {

  private final PackService packService;

  public PackController(PackService packService) {
    this.packService = packService;
  }

  @PostMapping("/{id}/activate")
  public ResponseEntity<?> activatePack(@PathVariable Long id) {
    try {
      Pack pack = packService.activatePackById(id);
      return ResponseEntity.ok(pack);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } }
}