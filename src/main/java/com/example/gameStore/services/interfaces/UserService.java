package com.example.gameStore.services.interfaces;

import com.example.gameStore.dtos.GameDtos.GameDto;
import com.example.gameStore.dtos.UserDtos.CreateUserRequestDto;
import com.example.gameStore.dtos.UserDtos.UpdateUserRequestDto;
import com.example.gameStore.dtos.UserDtos.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserDto> getUsers();

    Optional<UserDto> getUserById(UUID id);

    Optional<UserDto> createUser(CreateUserRequestDto newUser);

    boolean deleteUser(UUID id);

    Optional<UserDto> updateUser(UpdateUserRequestDto updateUser);

    Optional<UserDto> getCurrentUser();

    Optional<List<GameDto>> getFavouriteGames(String userId);

    boolean addFavouriteGame(String userId, String gameId);

    boolean removeFavouriteGame(String userId, String gameId);
}
