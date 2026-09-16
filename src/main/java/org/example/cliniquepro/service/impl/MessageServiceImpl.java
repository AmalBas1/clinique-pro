package org.example.cliniquepro.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collections;

import org.example.cliniquepro.service.MessageService;
import org.example.cliniquepro.dto.MessageDTO;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public MessageDTO save(MessageDTO messageDTO) { return null; }

    @Override
    public MessageDTO update(MessageDTO messageDTO) { return null; }

    @Override
    public List<MessageDTO> findAll() { return Collections.emptyList(); }

    @Override
    public MessageDTO findOne(Long id) { return null; }

    @Override
    public void delete(Long id) { }
}
