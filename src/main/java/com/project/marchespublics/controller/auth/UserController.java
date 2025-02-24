package com.project.marchespublics.controller.auth;


import com.project.marchespublics.dto.authDto.RegisterDto;
import com.project.marchespublics.service.interfaces.auth.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<RegisterDto> createUser(@RequestBody RegisterDto userDto) {
        RegisterDto createdUser = userService.create(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<Page<RegisterDto>> getAllUsers(Pageable pageable) {
        Page<RegisterDto> users = userService.findAll(pageable);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RegisterDto> updateUser(@PathVariable Long id, @RequestBody RegisterDto userDto) {
        RegisterDto updatedUser = userService.update(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
