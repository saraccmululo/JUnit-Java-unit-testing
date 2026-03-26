package com.sara.unittestingpractice.dto;

public class PackDTO {

  private Long packId;
  private Long gameId;
  private String status;

  public PackDTO(Long packId, Long gameId, String status){
    this.packId = packId;
    this.gameId = gameId;
    this.status = status;
  }

  public Long getPackId() { return packId; }
  public Long getGameId() { return gameId; }
  public String getStatus() { return status; }
}