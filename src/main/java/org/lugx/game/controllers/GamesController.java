package org.lugx.game.controllers;

import org.lugx.game.dtos.GameData;
import org.lugx.game.entities.GameEB;
import org.lugx.game.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "${api.prefix}")
public class GamesController {
    private final GameService gameService;

    @Autowired
    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "/game")
    public ResponseEntity<List<GameData>> getGames(@RequestParam(required = false) String title,
                                                   @RequestParam(required = false) String category) {
        return gameService.getGames(title, category);
    }

    @PostMapping(value = "/game")
    public ResponseEntity<GameEB> saveGame(@RequestBody GameData gameData) {
        return gameService.saveOrUpdateGame(gameData);
    }
}
