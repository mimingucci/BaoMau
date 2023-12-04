package mimingucci.baomau.controller;

import mimingucci.baomau.entity.User;
import mimingucci.baomau.exception.UserNotFoundException;
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
@RequestMapping(path = "/user", produces = "application/json")
@Validated
public class BaoMauController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public BaoMauController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") int id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        }catch (UserNotFoundException ex){
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user){
         User u= userService.createUser(user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getDescription());
         if(u==null){
             return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") int id, @RequestBody User user){
        try {
            return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") int id){
        userRepository.deleteById(id);
        return new ResponseEntity<>("Da xoa nguoi dung voi id: "+id, HttpStatus.ACCEPTED);
    }
}
