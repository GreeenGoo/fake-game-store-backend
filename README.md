# Fake Game Store Backend

Welcome to the **Fake Game Store Backend** project. This backend simulates an online game store by manipulating game store data. The project was developed over two intense weeks by me, alongside my team members [@Redmoor19](https://github.com/Redmoor19) and [@Olshanskaya](https://github.com/Olshanskaya), with further personal updates and improvements from my side.

## Table of Contents:
1. [Project Overview](#project-overview)
2. [Libraries and Functionality Used](#libraries-and-functionality-used)
3. [Setup](#setup)
   - [Local Setup](#local-setup)
   - [Online Setup](#online-setup)
4. [Project Features](#project-features)
5. [Usage](#usage)
   - [Authentication](#authentication-endpoints)
     - [Sign Up](#1-sign-up-unauthenticated-user)
     - [Login](#2-login-unauthenticated-user)
     - [Forgot Password](#3-forgot-password-unauthenticated-user)
     - [Reset Password](#4-reset-password-unauthenticated-user)
     - [Update Current User Password](#5-update-current-user-password-authenticated-user)
     - [Send Verification Code](#6-send-verification-code-authenticated-user)
     - [Verify User](#7-verify-user-authenticated-user)
   - [User](#user-endpoints)
     - [Get All Users](#1-get-all-users-admin-only)
     - [Get User By Id](#2-get-user-by-id-admin-only)
     - [Create User](#3-create-user-admin-only)
     - [Update User](#4-update-user-admin-only)
     - [Update User Role](#5-update-user-role-admin-only)
     - [Delete User](#6-delete-user-admin-only)
     - [Activate User](#7-activate-user-admin-only)
     - [Get Current User](#8-get-current-user-authenticated-user)
     - [Delete Current User](#9-delete-current-user-authenticated-user)
     - [Update Current User](#10-update-current-user-authenticated-user)
     - [Get Current User Favorite Games](#11-get-current-user-favorite-games-authenticated-user)
     - [Add Game to Favorites](#12-add-game-to-favorites-authenticated-user)
     - [Delete Game From Favorites](#13-delete-game-from-favorites-authenticated-user)
     - [Get Current User Games](#14-get-current-user-games-authenticated-user)
   - [Game](#game-endpoints)
     - [Get All Games](#1-get-all-games-admin-only)
     - [Get Active Games](#2-get-active-games-unauthenticated-or-authenticated-users)
     - [Create Game](#3-create-game-admin-only)
     - [Get Game By Id](#4-get-game-by-id-unauthenticated-or-authenticated-users)
     - [Update Game](#5-update-game-admin-only)
     - [Game Activation](#6-game-activation-admin-only)
     - [Game Deactivation](#7-game-deactivation-admin-only)
     - [Get Genres](#8-get-genres-unauthenticated-or-authenticated-users)
     - [Get Games By Genre](#9-get-games-by-genre-unauthenticated-or-authenticated-users)
     - [Get Player Support](#10-get-player-support-unauthenticated-or-authenticated-users)
     - [Get Games By Player Support](#11-get-games-by-player-support-unauthenticated-or-authenticated-users)
     - [Create Review](#12-create-review-authenticated-user)
     - [Update Review](#13-update-review-authenticated-user)
     - [Delete Review](#14-delete-review-authenticated-user)
     - [Add Game Key](#15-add-game-key-admin-only)
     - [Get Keys Amount](#16-get-keys-amount-admin-only)
   - [Order](#order-endpoints)
     - [Get All Orders](#1-get-all-orders-admin-only)
     - [Get All Extended Orders](#2-get-all-extended-orders-admin-only)
     - [Get Orders by User Id](#3-get-orders-by-user-id-admin-only)
     - [Get Order By Id](#4-get-order-by-id-admin-only)
     - [Get Current User Orders](#5-get-current-user-orders-authenticated-user)
     - [Get Current User Order By Id](#6-get-current-user-order-by-id-authenticated-user)
     - [Get Current User Cart](#7-get-current-user-cart-authenticated-user)
     - [Add Game To Cart](#8-add-game-to-cart-authenticated-user)
     - [Delete Game From Cart](#9-delete-game-from-cart-authenticated-user)
     - [Delete Current User Cart](#10-delete-current-user-cart-authenticated-user)
     - [Checkout Current Order](#11-checkout-current-order-authenticated-user)
     - [Pay Current Order](#12-pay-current-order-authenticated-user)

## Project Overview
The **Fake Game Store Backend** is designed to illustrate how a real-world online game store functions, simulating common e-commerce functionalities like user authentication, game data management, and order processing.

## Libraries and Functionality Used

### Spring Boot Starters
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Test
- Spring Boot Starter Validation
- Spring Boot Starter Mail
- Spring Boot Starter Security
- Spring Boot DevTools

### Database
- PostgreSQL Driver

### Mapping
- ModelMapper

### Utilities
- Lombok
- Javax Mail
- JWT (Java Web Token)
- BCrypt

## Setup

To begin using the Fake Game Store Backend, follow one of the methods below:

### Local Setup:
1. Create a PostgreSQL database on your local machine.
2. Download or clone this repository.
3. Add a `env.properties` file in the `src/main/resources` directory, containing the following fields:

    ```properties
    spring.datasource.url="your_postgreSQL_database_url"  
    # example: "jdbc:postgresql://localhost:5432/ecommerce"

    spring.datasource.username="your_postgreSQL_username"

    spring.datasource.password="your_postgreSQL_password"
    ```

### Online Setup:
For a hosted version, use the following URL to access the backend server:  
`https://fs18-java-backend-uladzislau-krukouski.onrender.com/api/v1`

**Note:**  
The backend is hosted on Render.com using a free tier, which may cause performance issues due to memory and speed limitations. Please be aware of potential latency or downtime.

## Project Features

The backend is split into four main functional areas:
1. **Authentication**
2. **Users**
3. **Games**
4. **Orders**

## Usage

After setup, you can interact with the backend to create and manage your own game store database. Below are examples of how to use the authentication and user features.

### Authentication Endpoints

### 1. **Sign Up (Unauthenticated User)**  
   `POST {{URL}}/auth/signup`  
   
   Requires body:
   
    {
      "name": "Vlad",
      "email": "vlad@gmail.com",
      "password": "12345678",
      "confirmPassword": "12345678"
    }
    
   Allows create a new user. The `name` field must be unique, and the `email` must be real, as email verification is required after account creation. The user is created with the `USER` role and an `UNVERIFIED` status. (See the "Send Verification Code" and "Verify User" endpoints for verification.)

### 2. **Login (Unauthenticated User)**  
   `POST {{URL}}/auth/login`  
   
   Requires body:
   
    {
      "email": "vlad@gmail.com",
      "password": "12345678"
    }
   
   Allows users to log in and receive a token that grants access to their personal profile and information.

### 3. **Forgot Password (Unauthenticated User)**  
   `POST {{URL}}/auth/forgot-password`  
   
   Requires body:
   
    {
      "email": "vlad@gmail.com"
    }
  
   Allows users to request a password reset if they forget it. After sending a request, the user will receive an email with a code, which they must enter in the [Reset Password](#4-reset-password-unauthenticated-user) endpoint. The code is valid for 15 minutes.

### 4. **Reset Password (Unauthenticated User)**  
   `POST {{URL}}/auth/reset-password/{code_from_email}`  
   
   Requires body:
   
    {
      "password": "123456789",
      "confirmPassword": "12345678"
    }

   Resets the user's password after receiving a code via email. It is only usable after the [Forgot Password](#3-forgot-password-unauthenticated-user) endpoint.

### 5. **Update Current User Password (Authenticated User)**  
   `POST {{URL}}/users/me/update-password`  
   
   Requires body:
   
    {
      "password": "old_password123",
      "newPassword": "new_password123",
      "newPasswordConfirm": "new_password123"
    }
   
   Allows an authenticated user to change their current password.

### 6. **Send Verification Code (Authenticated User)**  
   `POST {{URL}}/auth/verify/send-mail`  
   
   Sends a message to the user's email with a code for account verification. The code must be entered in the [Verify User](#7-verify-user-authenticated-user) endpoint to complete the verification process.

### 7. **Verify User (Authenticated User)**  
   `POST {{URL}}/auth/verify/{code_from_email}`  
   
   Verifies the user, changing their status to `ACTIVE`. After verification, users can place orders.

---

### User Endpoints

### 1. **Get All Users (Admin Only)**  
   `GET {{URL}}/users`  
   
   Allows an admin to lists all users and their information.

### 2. **Get User By Id (Admin Only)**  
   `GET {{URL}}/users/{user_id}`  
   
   Allows an admin to get information about a specific user.

### 3. **Create User (Admin Only)**  
   `POST {{URL}}/users`  
   
   Requires body:
   
    {
      "name": "NewUser",
      "email": "newuser@example.com",
      "password": "password123",
      "role": "USER"
    }
   
   Allows an admin to create a new user manually. The user's `role` in the query body can be either `USER` or `ADMIN`.

### 4. **Update User (Admin Only)**  
   `PATCH {{URL}}/users/{user_id}` 
   
   Requires body:
   
    {
      "name": "UpdatedUser",
      "email": "updateduser@example.com"
    }
    
   Allows an admin to update a user's details.

### 5. **Update User Role (Admin Only)**  
   `PATCH {{URL}}/users/{user_id}/role`  
   
   Requires body:
   
    {
      "role": "ADMIN"
    }
    
   Allows an admin to change a user's role between `USER` and `ADMIN`.

### 6. **Delete User (Admin Only)**  
   `DELETE {{URL}}/users/{user_id}`
   
   Deletes a specific user from the system.

### 7. **Activate User (Admin Only)**  
   `PATCH {{URL}}/users/{user_id}/activate`  
   
   Activates a specific user.

### 8. **Get Current User (Authenticated User)**  
   `GET {{URL}}/users/me`  
   
   Retrieves the authenticated user's profile information.

### 9. **Delete Current User (Authenticated User)**  
   `DELETE {{URL}}/users/me`  
   
   Allows the current authenticated user to delete their account.

### 10. **Update Current User (Authenticated User)**  
   `PATCH {{URL}}/users/me`  
   
   Requires body:
   
    {
      "name": "UpdatedName",
      "email": "updatedemail@example.com"
    }
   
   Allows the authenticated user to update their personal details.

### 11. **Get Current User Favorite Games (Authenticated User)**  
   `GET {{URL}}/users/me/favorites`  
   
   Retrieves the list of favorite games for the authenticated user.

### 12. **Add Game to Favorites (Authenticated User)**  
   `POST {{URL}}/users/me/favorites/{game_id}`  
   
   Adds a game to the authenticated user's favorites list.

### 13. **Delete Game From Favorites (Authenticated User)**  
   `DELETE {{URL}}/users/me/favorites/{game_id}`  
   
   Removes a game from the authenticated user's favorites list.

### 14. **Get Current User Games (Authenticated User)**  
   `GET {{URL}}/users/me/games`  
   
   Retrieves the list of games owned by the authenticated user.

---

### Game Endpoints

### 1. **Get All Games (Admin Only)**  
   `GET {{URL}}/games`  
   
   Retrieves a list of all games in the store. Only accessible to admins.

### 2. **Get Active Games (Unauthenticated or Authenticated Users)**  
   `GET {{URL}}/games/active` 
   
   Retrieves a list of active games visible to the public.

### 3. **Create Game (Admin Only)**  
   `POST {{URL}}/games`  
   
   Requires body:
   
    {
       "name": "game_title",
       "genreList": [
           "genre_1",
           "genre_2",
           "genre_3"
       ],
       "thumbnail": "thumbnail_url",
       "images": [
           "image_url_1",
           "image_url_2"
       ],
       "developer": "developer_name",
       "releaseDate": "string_date",
       "systemRequirements": "all_system_requirenments",
       "playerSupport": [
           "player_support_1",
           "player_support_2"
       ],
       "price": ”price_number”,
       "description": "description"
    }

  
   Allows an admin to add a new game to the store.

### 4. **Get Game By Id (Unauthenticated or Authenticated Users)**  
   `GET {{URL}}/games/{game_id}`  
   
   Retrieves details of a specific game by its ID.

### 5. **Update Game (Admin Only)**  
   `PATCH {{URL}}/games/{game_id}`  

   Requires body:
   
    {
       "id": "game_id",
       "name": "game_title",
       "genreList": [
           "genre_1",
           "genre_2",
           "genre_3"
       ],
       "thumbnail": "thumbnail_url",
       "images": [
           "image_url_1",
           "image_url_2"
       ],
       "developer": "developer_name",
       "releaseDate": "string_date",
       "systemRequirements": "all_system_requirenments",
       "playerSupport": [
           "player_support_1",
           "player_support_2"
       ],
       "price": ”price_number”,
       "description": "description"
    }
  
   Allows an admin to update game details.

### 6. **Game Activation (Admin Only)**  
   `PATCH {{URL}}/games/{game_id}/activate`
   
   Activates a game, making it visible in the store.

### 7. **Game Deactivation (Admin Only)**  
   `PATCH {{URL}}/games/{game_id}/deactivate` 
   
   Deactivates a game, hiding it from the store.

### 8. **Get Genres (Unauthenticated or Authenticated Users)**  
   `GET {{URL}}/games/genres`  
   
   Retrieves a list of available game genres.

### 9. **Get Games By Genre (Unauthenticated or Authenticated Users)**  
   `GET {{URL}}/games/genre/{genre}`  
   
   Retrieves games that belong to a specific genre.

### 10. **Get Player Support (Unauthenticated or Authenticated Users)**  
   `GET {{URL}}/games/player-support`
    
   Retrieves information about the types of player support (e.g., single-player, multiplayer).

### 11. **Get Games By Player Support (Unauthenticated or Authenticated Users)**  
   `GET {{URL}}/games/player-support/{support_type}` 
    
   Retrieves games based on the type of player support (e.g., multiplayer games).

### 12. **Create Review (Authenticated User)**  
   `POST {{URL}}/games/{game_id}/reviews`
    
   Requires body:
    
      {
         "rating": "rating_number_between_1_and_5",
         "comment": "review_description"
      }
 
   Allows an authenticated user to add a review to a game.

### 13. **Update Review (Authenticated User)**  
   `PATCH {{URL}}/games/{game_id}/reviews/{review_id}`
     
   Requires body:
    
      {
         "rating": "rating_number_between_1_and_5",
         "comment": "review_description"
      }
 
   Updates a user's review for a specific game.

### 14. **Delete Review (Authenticated User)**  
   `DELETE {{URL}}/games/{game_id}/reviews/{review_id}`
    
   Deletes a user's review for a specific game.

### 15. **Add Game Key (Admin Only)**  
   `POST {{URL}}/games/{game_id}/keys`  
    
   Allows an admin to add keys for a specific game.

### 16. **Get Keys Amount (Admin Only)**  
   `GET {{URL}}/games/{game_id}/keys/amount`  
    
   Retrieves the number of available keys for a specific game.

---

### Order Endpoints

### 1. **Get All Orders (Admin Only)**  
   `GET {{URL}}/orders`  
   
   Retrieves a list of all orders placed in the store. Only accessible to admins.

### 2. **Get All Extended Orders (Admin Only)**  
   `GET {{URL}}/orders/extended`  
   
   Retrieves all orders with detailed information, including user and game details.

### 3. **Get Orders by User Id (Admin Only)**  
   `GET {{URL}}/orders/user/{user_id}`  
   
   Retrieves orders for a specific user by their ID.

### 4. **Get Order By Id (Admin Only)**  
   `GET {{URL}}/orders/{order_id}`  
   
   Retrieves the details of a specific order by its ID.

### 5. **Get Current User Orders (Authenticated User)**  
   `GET {{URL}}/orders/me`  
   
   Retrieves a list of orders placed by the authenticated user.

### 6. **Get Current User Order By Id (Authenticated User)**  
   `GET {{URL}}/orders/me/{order_id}` 
   
   Retrieves the details of a specific order placed by the authenticated user.

### 7. **Get Current User Cart (Authenticated User)**  
   `GET {{URL}}/orders/me/cart` 
   
   Retrieves the authenticated user's current cart.

### 8. **Add Game To Cart (Authenticated User)**  
   `POST {{URL}}/orders/me/cart/{game_id}` 
   
   Adds a game to the authenticated user's cart.

### 9. **Delete Game From Cart (Authenticated User)**  
   `DELETE {{URL}}/orders/me/cart/{game_id}`  
   
   Removes a game from the authenticated user's cart.

### 10. **Delete Current User Cart (Authenticated User)**  
   `DELETE {{URL}}/orders/me/cart`
    
   Clears the authenticated user's cart.

### 11. **Checkout Current Order (Authenticated User)**  
   `POST {{URL}}/orders/me/checkout`  
    
   Confirms the current order and proceeds to the payment stage.

### 12. **Pay Current Order (Authenticated User)**  
   `POST {{URL}}/orders/me/pay` 

   Requires body:
    
      {
         "orderId": "order_id",
         "isPaidSuccessfully": true
      }

   Processes the MOK payment for the current order.

## Thank you for taking the time to look at my project! I would greatly appreciate any feedback or contributions you may have. 😊
