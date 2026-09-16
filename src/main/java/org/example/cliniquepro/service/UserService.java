package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long id);
    Page<UserDTO> getAllUsers(int page, int size, String sort);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}
