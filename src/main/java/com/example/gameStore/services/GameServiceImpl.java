package com.example.gameStore.services;

import com.example.gameStore.dtos.GameDtos.CreateGameRequestDto;
import com.example.gameStore.dtos.GameDtos.GameDto;
import com.example.gameStore.dtos.GameDtos.SingleGameWithReviewsDto;
import com.example.gameStore.dtos.KeyDto.KeyCreationDto;
import com.example.gameStore.dtos.ReviewDtos.EmbeddedReviewDto;
import com.example.gameStore.dtos.ReviewDtos.ReviewDto;
import com.example.gameStore.entities.Game;
import com.example.gameStore.entities.Key;
import com.example.gameStore.entities.Review;
import com.example.gameStore.entities.User;
import com.example.gameStore.enums.Genre;
import com.example.gameStore.enums.PlayerSupport;
import com.example.gameStore.repositories.GameRepository;
import com.example.gameStore.repositories.KeyRepository;
import com.example.gameStore.repositories.ReviewRepository;
import com.example.gameStore.repositories.UserRepository;
import com.example.gameStore.services.interfaces.GameService;
import com.example.gameStore.utilities.GameUtilities;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private KeyRepository keyRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<GameDto> findAllGames(String sortField, String sortOrder) {
        boolean isValidField = Arrays.stream(Game.class.getDeclaredFields())
                .anyMatch(f -> f.getName().equals(sortField));

        if (!isValidField) {
            throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(0, 1000, Sort.by(direction, sortField));
        Page<Game> page = gameRepository.findAll(pageable);
        return page.getContent()
                .stream()
                .map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GameDto> findAllActiveGames(String sortField, String sortOrder) {
        boolean isValidField = Arrays.stream(Game.class.getDeclaredFields())
                .anyMatch(f -> f.getName().equals(sortField));

        if (!isValidField) {
            throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(0, 1000, Sort.by(direction, sortField));
        Page<Game> page = gameRepository.findAllByIsActiveTrue(pageable);
        return page.getContent()
                .stream()
                .map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SingleGameWithReviewsDto> getGameById(String id) {
        Optional<Game> game = gameRepository.findById(UUID.fromString(id));
        if (game.isEmpty()) return Optional.empty();
        List<EmbeddedReviewDto> reviews = reviewRepository.findReviewsByGameId(UUID.fromString(id));
        SingleGameWithReviewsDto singleGameWithReviewsDto = modelMapper.map(game, SingleGameWithReviewsDto.class);
        singleGameWithReviewsDto.setReviews(reviews);
        return Optional.of(singleGameWithReviewsDto);
    }

    @Override
    public Optional<GameDto> createGame(CreateGameRequestDto createGameRequestDto) {
        Game createGame = modelMapper.map(createGameRequestDto, Game.class);
        createGame.setSku(GameUtilities.generateSku());
        gameRepository.save(createGame);
        return Optional.of(modelMapper.map(createGame, GameDto.class));

    }

    @Override
    public Optional<GameDto> updateGame(String id, GameDto gameDto) {
        return Optional.of(new GameDto(UUID.fromString(id), "Cyber City", List.of(Genre.ACTION), 92,
                "http://example.com/thumb5.jpg", List.of("http://example.com/image5.jpg"),
                "Cyber Devs", new Date(), "16GB RAM, 6GB GPU", List.of(PlayerSupport.ONLINE_COMPETITIVE),
                49.99f, "An action-packed cyber adventure", "SKU11223", true, 1));
    }

    @Override
    public boolean deleteGame(String id) {
        return true;
    }

    @Override
    public List<String> getAllGenres() {
        return gameRepository.getAllGenresList();
    }

    @Override
    public List<GameDto> getGamesByGenre(String genre) {
        List<Game> gamesByGenre = gameRepository.getGamesByGenre(genre);
        return modelMapper.map(gamesByGenre, new TypeToken<List<GameDto>>() {
        }.getType());
    }

    @Override
    public Optional<ReviewDto> createReview(String gameId, String userId, ReviewDto reviewDto) {
        Review review = modelMapper.map(reviewDto, Review.class);
        Optional<User> optUser = userRepository.findById(UUID.fromString(userId));
        Optional<Game> optGame = gameRepository.findById(UUID.fromString(gameId));
        if (optUser.isEmpty() || optGame.isEmpty()) return Optional.empty();
        review.setUserId(optUser.get());
        review.setGameId(optGame.get());
        Review savedReview = reviewRepository.save(review);
        return Optional.of(modelMapper.map(savedReview, ReviewDto.class));
    }

    @Override
    public Optional<ReviewDto> updateReview(String gameId, String reviewId) {
        System.out.println("============================" + gameId + "***" + reviewId + "============================");
        return Optional.empty();
    }

    @Override
    public boolean deleteReview(String gameId, String reviewId) {
        System.out.println("============================" + gameId + "***" + reviewId + "============================");
        return true;
    }

    @Override
    public Optional<KeyCreationDto> addKeyToGame(String gameId) {
        Game game = gameRepository.findById(UUID.fromString(gameId))
                .orElseThrow(() -> new RuntimeException("Game not found with ID: " + gameId));
        Key key = new Key();
        key.setGame(game);
        keyRepository.save(key);
        game.setQuantity(game.getQuantity() + 1);
        gameRepository.save(game);
        return Optional.of(modelMapper.map(key, KeyCreationDto.class));
    }

    @Override
    public Optional<Integer> countGameKeys(String gameId) {
        UUID convertedGameId = UUID.fromString(gameId);
        return gameRepository.getGameKeysAmount(convertedGameId);
    }
}
