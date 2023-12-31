package mimingucci.baomau.service;

import mimingucci.baomau.entity.Message;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.MessageRepository;
import mimingucci.baomau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserService userService, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

}
