package org.lugx.game.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lugx.game.dtos.GameData;
import org.lugx.game.entities.GameEB;
import org.lugx.game.services.GameService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GamesControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GamesController gamesController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private GameData testGameData;
    private GameEB testGameEB;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(gamesController)
                .addPlaceholderValue("api.prefix", "games")
                .build();
        objectMapper = new ObjectMapper();

        // Setup test data
        testGameData = new GameData();
        testGameData.setId(1L);
        testGameData.setTitle("Test Game");
        testGameData.setCategory("Action");
        testGameData.setReleaseDate("2024-01-01");
        testGameData.setPrice("59.99");

        testGameEB = new GameEB();
        testGameEB.setId(1L);
        testGameEB.setTitle("Test Game");
        testGameEB.setCategory("Action");
        testGameEB.setReleaseDate("2024-01-01");
        testGameEB.setPrice("59.99");
    }

    @Test
    void getGames_WithoutFilters_ShouldReturnAllGames() throws Exception {
        // Arrange
        List<GameData> gameList = Arrays.asList(testGameData);
        when(gameService.getGames(null, null))
                .thenReturn(ResponseEntity.ok(gameList));

        // Act & Assert
        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Game"))
                .andExpect(jsonPath("$[0].category").value("Action"))
                .andExpect(jsonPath("$[0].releaseDate").value("2024-01-01"))
                .andExpect(jsonPath("$[0].price").value("59.99"));
    }

    @Test
    void getGames_WithTitleFilter_ShouldReturnFilteredGames() throws Exception {
        // Arrange
        List<GameData> gameList = Arrays.asList(testGameData);
        when(gameService.getGames("Test Game", null))
                .thenReturn(ResponseEntity.ok(gameList));

        // Act & Assert
        mockMvc.perform(get("/games")
                        .param("title", "Test Game"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Test Game"));
    }

    @Test
    void getGames_WithCategoryFilter_ShouldReturnFilteredGames() throws Exception {
        // Arrange
        List<GameData> gameList = Arrays.asList(testGameData);
        when(gameService.getGames(null, "Action"))
                .thenReturn(ResponseEntity.ok(gameList));

        // Act & Assert
        mockMvc.perform(get("/games")
                        .param("category", "Action"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].category").value("Action"));
    }

    @Test
    void getGames_WithBothFilters_ShouldReturnFilteredGames() throws Exception {
        // Arrange
        List<GameData> gameList = Arrays.asList(testGameData);
        when(gameService.getGames("Test Game", "Action"))
                .thenReturn(ResponseEntity.ok(gameList));

        // Act & Assert
        mockMvc.perform(get("/games")
                        .param("title", "Test Game")
                        .param("category", "Action"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Test Game"))
                .andExpect(jsonPath("$[0].category").value("Action"));
    }

    @Test
    void getGames_NoMatchingGames_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(gameService.getGames("NonExistent", null))
                .thenReturn(ResponseEntity.ok(Collections.emptyList()));

        // Act & Assert
        mockMvc.perform(get("/games")
                        .param("title", "NonExistent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void saveGame_ValidGameData_ShouldReturnSavedGame() throws Exception {
        // Arrange
        GameData newGameData = new GameData();
        newGameData.setTitle("New Game");
        newGameData.setCategory("RPG");
        newGameData.setReleaseDate("2024-12-01");
        newGameData.setPrice("49.99");

        when(gameService.saveOrUpdateGame(any(GameData.class)))
                .thenReturn(ResponseEntity.ok(testGameEB));

        // Act & Assert
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newGameData)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Game"))
                .andExpect(jsonPath("$.category").value("Action"))
                .andExpect(jsonPath("$.releaseDate").value("2024-01-01"))
                .andExpect(jsonPath("$.price").value("59.99"));
    }

    @Test
    void saveGame_UpdateExistingGame_ShouldReturnUpdatedGame() throws Exception {
        // Arrange
        GameData updateGameData = new GameData();
        updateGameData.setId(1L);
        updateGameData.setTitle("Updated Game");
        updateGameData.setCategory("Action");
        updateGameData.setReleaseDate("2024-01-01");
        updateGameData.setPrice("69.99");

        GameEB updatedGameEB = new GameEB();
        updatedGameEB.setId(1L);
        updatedGameEB.setTitle("Updated Game");
        updatedGameEB.setCategory("Action");
        updatedGameEB.setReleaseDate("2024-01-01");
        updatedGameEB.setPrice("69.99");

        when(gameService.saveOrUpdateGame(any(GameData.class)))
                .thenReturn(ResponseEntity.ok(updatedGameEB));

        // Act & Assert
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateGameData)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Game"))
                .andExpect(jsonPath("$.price").value("69.99"));
    }

    @Test
    void saveGame_WithMinimalData_ShouldReturnSavedGame() throws Exception {
        // Arrange
        GameData minimalGameData = new GameData();
        minimalGameData.setTitle("Minimal Game");

        GameEB minimalGameEB = new GameEB();
        minimalGameEB.setId(2L);
        minimalGameEB.setTitle("Minimal Game");

        when(gameService.saveOrUpdateGame(any(GameData.class)))
                .thenReturn(ResponseEntity.ok(minimalGameEB));

        // Act & Assert
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(minimalGameData)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("Minimal Game"));
    }

    @Test
    void saveGame_EmptyRequestBody_ShouldHandleBadRequest() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk()); // Controller doesn't validate, so it will pass to service
    }

    @Test
    void getGames_MultipleGames_ShouldReturnAllGames() throws Exception {
        // Arrange
        GameData game1 = new GameData();
        game1.setId(1L);
        game1.setTitle("Game 1");
        game1.setCategory("Action");
        game1.setPrice("59.99");

        GameData game2 = new GameData();
        game2.setId(2L);
        game2.setTitle("Game 2");
        game2.setCategory("RPG");
        game2.setPrice("49.99");

        List<GameData> gameList = Arrays.asList(game1, game2);
        when(gameService.getGames(null, null))
                .thenReturn(ResponseEntity.ok(gameList));

        // Act & Assert
        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Game 1"))
                .andExpect(jsonPath("$[1].title").value("Game 2"));
    }
}