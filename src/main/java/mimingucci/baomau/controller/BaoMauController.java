package mimingucci.baomau.controller;

import mimingucci.baomau.entity.Message;
import mimingucci.baomau.entity.State;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.entity.UserDTO;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.StateRepository;
import mimingucci.baomau.repository.UserDTORepository;
import mimingucci.baomau.repository.UserRepository;
import mimingucci.baomau.service.MessageService;
import mimingucci.baomau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/user")
@Validated
public class BaoMauController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTORepository userDTORepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private MessageService messageService;

    public BaoMauController(UserService userService, UserRepository userRepository, UserDTORepository userDTORepository, StateRepository stateRepository, MessageService messageService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userDTORepository = userDTORepository;
        this.stateRepository = stateRepository;
        this.messageService = messageService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/rating/{pageNum}")
    public ResponseEntity<?> getByRating(@PathVariable(name = "pageNum") int pageNum){
        return new ResponseEntity<>(userService.listByRating(pageNum), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> search(@RequestParam(name = "query") String keyword){
        return new ResponseEntity<>(userService.search(keyword, 1), HttpStatus.OK);
    }

    @GetMapping(path = "/get/{nickname}")
    public ResponseEntity<?> getUserByNickname(@PathVariable(name = "nickname") String nickname) {
        try {
            User user = userService.findUserByNickname(nickname);
            return ResponseEntity.ok(user);
        }catch (UserNotFoundException ex){
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/topcontributors")
    public ResponseEntity<?> getTopContributors(){
        return new ResponseEntity<>(userService.topContrubutions(), HttpStatus.OK);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user){
         User u= userService.createUser(user.getNickname(), user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getDescription());
         if(u==null){
             return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/{nickname}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "nickname") String nickname, @RequestBody User user){
        try {
            return new ResponseEntity<>(userService.updateUser(nickname, user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/update/state/{nickname}")
    public ResponseEntity<?> updateStateUser(@PathVariable(name = "nickname") String nickname, @RequestParam(name = "state") String state){
        State state1=stateRepository.findByName(state);
        User user=userRepository.findByNickname(nickname);
        user.setState(state1);
        User savedUser=userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/update/message/create")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        try {
            messageService.addMessage(message);
            return new ResponseEntity<>("Da tao message", HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/delete/{nickname}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "nickname") String nickname){
        userRepository.deleteByNickname(nickname);
        userDTORepository.deleteByNickname(nickname);
        return new ResponseEntity<>("Da xoa nguoi dung voi nickname: "+nickname, HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/update/enabled/{status}")
    public ResponseEntity<?> updateEnabled(@RequestParam(name = "nickname") String nickname, @PathVariable(name = "status") Boolean enabled){
        try {
            userService.updateEnabled(nickname, enabled);
            return new ResponseEntity<>("Da update enable staus cua nguoi dung voi nickname: "+nickname, HttpStatus.ACCEPTED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
