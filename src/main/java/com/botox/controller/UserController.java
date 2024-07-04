package com.botox.controller;

import com.botox.domain.ProfileDto;
import com.botox.domain.User;
import com.botox.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 유저 조회
    @GetMapping("/{userId}")
    public ResponseForm<User> getUserByUserId(@PathVariable String userId) {
        User user = userService.getUserByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return new ResponseForm<>(HttpStatus.OK, user, "User retrieved successfully");
    }

    // 유저 정보 수정
    @PatchMapping("/{userId}")
    public ResponseForm<User> updateUser(@PathVariable String userId, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userId, userDetails);
        return new ResponseForm<>(HttpStatus.OK, updatedUser, "User updated successfully");
    }

    // 유저 삭제(회원탈퇴)
    @DeleteMapping("/{userId}")
    public ResponseForm<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ResponseForm<>(HttpStatus.NO_CONTENT, null, "User deleted successfully");
    }

    // userProfile 수정 또는 생성
    @PatchMapping("/{userId}/profile")
    public ResponseForm<User> updateUserProfile(@PathVariable String userId, @RequestBody Map<String, String> updates) {
        String userProfile = updates.get("userProfile");
        String userProfilePic = updates.get("userProfilePic");
        User updatedUser = userService.updateUserProfile(userId,userProfile, userProfilePic);
        return new ResponseForm<>(HttpStatus.OK, updatedUser, "User profile updated successfully");
    }

    // userProfile 삭제
    @DeleteMapping("/{userId}/profile")
    public ResponseForm<User> deleteUserProfile(@PathVariable String userId) {
        User updatedUser = userService.deleteUserProfile(userId);
        return new ResponseForm<>(HttpStatus.OK, updatedUser, "User profile deleted successfully");
    }

    // userProfile 조회
    @GetMapping("/{userId}/profile")
    public ResponseForm<ProfileDto> getUserProfile(@PathVariable String userId) {
        ProfileDto userProfile = userService.getUserProfile(userId);
        return new ResponseForm<>(HttpStatus.OK, userProfile, "User profile retrieved successfully");
    }

    // 비밀번호 변경
    @PatchMapping("/{userId}/password")
    public ResponseForm<User> updateUserPassword(@PathVariable String userId, @RequestBody String password) {
        User updatedUser = userService.updateUserPassword(userId, password);
        return new ResponseForm<>(HttpStatus.OK, updatedUser, "User password updated successfully");
    }

}
