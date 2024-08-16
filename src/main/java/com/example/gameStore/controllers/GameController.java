package com.example.gameStore.controllers;


import com.example.gameStore.dtos.GameDtos.CreateGameRequestDto;
import com.example.gameStore.dtos.GameDtos.GameDto;
import com.example.gameStore.dtos.GameDtos.GamesListResponseDto;
import com.example.gameStore.dtos.GameDtos.SingleGameWithReviewsDto;
import com.example.gameStore.dtos.GameDtos.UpdateGameRequestDto;
import com.example.gameStore.dtos.KeyDto.KeyCreationDto;
import com.example.gameStore.dtos.ReviewDtos.CreateOrUpdateReviewRequestDto;
import com.example.gameStore.dtos.ReviewDtos.ReviewDto;
import com.example.gameStore.enums.Genre;
import com.example.gameStore.enums.PlayerSupport;
import com.example.gameStore.services.interfaces.GameService;
import jakarta.servlet.http.HttpServletRequest;
import com.example.gameStore.utilities.TypeConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class GameController {

    @Autowired
    private final GameService gameService;

    @GetMapping("games/all")
    public ResponseEntity<GamesListResponseDto> findAllGames(
            @RequestParam(value = "sort", defaultValue = "name") String sortField,
            @RequestParam(value = "order", defaultValue = "asc") String sortOrder,
            @RequestParam(value = "page", defaultValue = "1") String pageNumber,
            @RequestParam(value = "size", defaultValue = "20") String pageSize,
            @RequestParam(value = "search", required = false) String searchKeyword,
            @RequestParam(value = "genres", required = false) List<String> genres,
            @RequestParam(value = "playerSupports", required = false) List<String> playerSupport
    ) {

        int convertedPageNumber = TypeConverter.convertStringToInt(pageNumber, "Wrong data format for page number");
        int convertedPageSize = TypeConverter.convertStringToInt(pageSize, "Wrong data format for page size");

        GamesListResponseDto games = gameService.findAllGames(
                sortField,
                sortOrder,
                convertedPageNumber,
                convertedPageSize,
                searchKeyword,
                genres,
                playerSupport
        );
        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(games);
    }

    @GetMapping("games/active")
    public ResponseEntity<GamesListResponseDto> findAllActiveGames(
            @RequestParam(value = "sort", defaultValue = "name") String sortField,
            @RequestParam(value = "order", defaultValue = "asc") String sortOrder,
            @RequestParam(value = "page", defaultValue = "1") String pageNumber,
            @RequestParam(value = "size", defaultValue = "20") String pageSize,
            @RequestParam(value = "search", required = false) String searchKeyword,
            @RequestParam(value = "genres", required = false) List<String> genres,
            @RequestParam(value = "playerSupports", required = false) List<String> playerSupport) {

        int convertedPageNumber = TypeConverter.convertStringToInt(pageNumber, "Wrong data format for page number");
        int convertedPageSize = TypeConverter.convertStringToInt(pageSize, "Wrong data format for page size");

        GamesListResponseDto games = gameService.findAllActiveGames(
                sortField,
                sortOrder,
                convertedPageNumber,
                convertedPageSize,
                searchKeyword,
                genres,
                playerSupport);
        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(games);
    }

    @GetMapping("games/{id}")
    public ResponseEntity<SingleGameWithReviewsDto> getGameById(@PathVariable(required = true, name = "id") String id) {
        Optional<SingleGameWithReviewsDto> gameWithReviews = gameService.getGameById(id);
        return gameWithReviews.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("games")
    public ResponseEntity<GameDto> createGame(@RequestBody CreateGameRequestDto createGameRequestDto) {
        Optional<GameDto> game = gameService.createGame(createGameRequestDto);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("games")
    public ResponseEntity<GameDto> updateGame(@RequestBody UpdateGameRequestDto updateGameRequestDto) {
        Optional<GameDto> game = gameService.updateGame(updateGameRequestDto);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("games/deactivation/{gameId}")
    public ResponseEntity<GameDto> deactivateGame(@PathVariable String gameId) {
        Optional<GameDto> game = gameService.deactivateGame(gameId);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("games/activation/{gameId}")
    public ResponseEntity<GameDto> activateGame(@PathVariable String gameId) {
        Optional<GameDto> game = gameService.activateGame(gameId);
        return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("games/genres")
    public ResponseEntity<List<String>> getAllGenres() {
        List<String> genreList = Genre.getAllGenresString();
        if (genreList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(genreList);
    }

    @GetMapping("games/genres/{genre}")
    public ResponseEntity<List<GameDto>> getGamesByGenre(@PathVariable String genre) {
        List<GameDto> gamesByGenre = gameService.getGamesByGenre(genre);
        if (gamesByGenre.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(gamesByGenre);
    }

    @GetMapping("games/player-support")
    public ResponseEntity<List<String>> getAllPlayerSupport() {
        List<String> playerSupport = PlayerSupport.getAllPlayerSupportString();
        if (playerSupport.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(playerSupport);
    }

    @GetMapping("games/player-support/{playerSupport}")
    public ResponseEntity<List<GameDto>> getGamesByPlayerSupport(@PathVariable String playerSupport) {
        List<GameDto> gamesByPlayerSupport = gameService.getGamesByPlayerSupport(playerSupport);
        if (gamesByPlayerSupport.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(gamesByPlayerSupport);
    }

    @PostMapping("games/reviews/{gameId}")
    public ResponseEntity<ReviewDto> createReview(HttpServletRequest request, @PathVariable String gameId, @RequestBody CreateOrUpdateReviewRequestDto reviewDto) {
        String userId = (String) request.getAttribute("userId");
        Optional<ReviewDto> review = gameService.createReview(gameId, userId, reviewDto);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("games/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(HttpServletRequest request, @PathVariable String reviewId, @RequestBody CreateOrUpdateReviewRequestDto createOrUpdateReviewRequestDto) {
        String userId = (String) request.getAttribute("userId");
        Optional<ReviewDto> review = gameService.updateReview(reviewId, userId, createOrUpdateReviewRequestDto);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("games/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> deleteReview(HttpServletRequest request, @PathVariable String reviewId) {
        String userId = (String) request.getAttribute("userId");
        if (gameService.deleteReview(reviewId, userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("games/keys/{gameId}")
    public ResponseEntity<KeyCreationDto> addKeyToGame(@PathVariable String gameId) {
        Optional<KeyCreationDto> key = gameService.addKeyToGame(gameId);
        return key.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("games/keys/{gameId}")
    public ResponseEntity<Integer> getGameKeysAmount(@PathVariable String gameId) {
        Optional<Integer> keysAmountOpt = gameService.countGameKeys(gameId);
        return keysAmountOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
