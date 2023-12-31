package mimingucci.baomau.controller;

import mimingucci.baomau.entity.Message;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.MessageRepository;
import mimingucci.baomau.repository.UserRepository;
import mimingucci.baomau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/message")
@Validated
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Message message) {
        User author;
        try {
            author = userService.findUserByNickname(message.getAuthor());
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        User recipient;
        try {
            recipient = userService.findUserByNickname(message.getRecipient());
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (message.getContent() == null || message.getContent().length() == 0) {
            return new ResponseEntity<>("Content khong duoc trong", HttpStatus.BAD_REQUEST);
        }
        message.setTime(new Date());
        message.setSeen(false);
        Message savedMessage = messageRepository.save(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    private String choose(Message message, String match){
        if(message.getAuthor().equals(match)){
            return message.getRecipient();
        }else{
            return message.getAuthor();
        }
    }
    @GetMapping(path = "/oldmessages")
    public ResponseEntity<?> getMessageByAuthorAndRecipient(@RequestParam(name = "author") String author, @RequestParam(name = "recipient") String recipient){
        List<Message> messages=messageRepository.findByAuthorAndRecipient(author, recipient);
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        return new ResponseEntity<>(messages, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/get/{nickname}")
    public ResponseEntity<?> getMessagesByNickname(@PathVariable(name = "nickname") String nickname){
        try {
            User user=userService.findUserByNickname(nickname);
            List<Message> messages=messageRepository.findByNickname(nickname);
            if(messages.isEmpty()){
                return new ResponseEntity<>("Empty", HttpStatus.OK);
            }
            List<List<Message>> messageByUser=new ArrayList<>();
            messageByUser.add(new ArrayList<>());
            Collections.sort(messages, new Comparator<Message>() {
                @Override
                public int compare(Message o1, Message o2) {
                    return choose(o1, nickname).compareTo(choose(o2, nickname));
                }
            });
            messageByUser.get(0).add(messages.get(0));
            for(int i=1; i<messages.size(); i++){
                if(choose(messages.get(i), nickname).equals(choose(messages.get(i-1), nickname))){
                    messageByUser.get(messageByUser.size()-1).add(messages.get(i));
                }else{
                    messageByUser.add(new ArrayList<>());
                    messageByUser.get(messageByUser.size()-1).add(messages.get(i));
                }
            }
            for(int i=0; i<messageByUser.size(); i++){
                Collections.sort(messageByUser.get(i), new Comparator<Message>() {
                    @Override
                    public int compare(Message o1, Message o2) {
                        return o2.getTime().compareTo(o1.getTime());
                    }
                });
            }
            if(messageByUser.size()>1)
            Collections.sort(messageByUser, new Comparator<List<Message>>() {
                @Override
                public int compare(List<Message> o1, List<Message> o2) {
                    if(!o2.get(o2.size()-1).getSeen() && o1.get(o1.size()-1).getSeen()){
                        return 1;
                    }
                    return o2.get(o2.size()-1).getTime().compareTo(o1.get(o1.size()-1).getTime());
                }
            });
            return new ResponseEntity<>(messageByUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/status")
    public ResponseEntity<?> updateSeenStatus(@RequestParam(name = "author") String author, @RequestParam(name = "recipient") String recipient, @RequestParam(name = "status") Boolean status){
        messageRepository.updateSeenStatus(author, recipient, status);
        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }
}
