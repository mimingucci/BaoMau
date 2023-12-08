package mimingucci.baomau.controller;

import mimingucci.baomau.entity.User;
import mimingucci.baomau.entity.UserDTO;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.UserDTORepository;
import mimingucci.baomau.repository.UserRepository;
import mimingucci.baomau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
@Validated
public class BaoMauController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTORepository userDTORepository;

    public BaoMauController(UserService userService, UserRepository userRepository, UserDTORepository userDTORepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userDTORepository = userDTORepository;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
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
