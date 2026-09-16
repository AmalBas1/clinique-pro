package org.example.cliniquepro.service;

import org.example.cliniquepro.dto.MessageDTO;
import java.util.List;

public interface MessageService {
    MessageDTO save(MessageDTO messageDTO);
    MessageDTO update(MessageDTO messageDTO);
    List<MessageDTO> findAll();
    MessageDTO findOne(Long id);
    void delete(Long id);
}
