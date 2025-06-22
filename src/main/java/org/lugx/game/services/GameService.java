package org.lugx.game.services;

import org.lugx.game.dtos.GameData;
import org.lugx.game.entities.GameEB;
import org.lugx.game.repos.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private final GameRepo gameRepository;

    @Autowired
    public GameService(GameRepo gameRepository) {
        this.gameRepository = gameRepository;
    }

    public ResponseEntity<List<GameData>> getGames(String title, String category) {
        List<GameEB> results = gameRepository.findAll(GameSpecification.filterBy(title, category));

        List<GameData> games = results.stream().map(result -> {
            GameData gameData = new GameData();
            gameData.mapToGameData(result);
            return gameData;
        }).toList();

        return ResponseEntity.ok(games);
    }

    public ResponseEntity<GameEB> saveOrUpdateGame(GameData gameData) {
        GameEB results = gameRepository.save(gameData.mapToGameEB());
        return ResponseEntity.ok(results);
    }
}
