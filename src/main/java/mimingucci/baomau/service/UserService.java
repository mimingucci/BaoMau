package mimingucci.baomau.service;

import mimingucci.baomau.entity.AuthenticationType;
import mimingucci.baomau.entity.Comment;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.entity.UserDTO;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.UserDTORepository;
import mimingucci.baomau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTORepository userDTORepository;

    public UserService(UserRepository userRepository, UserDTORepository userDTORepository) {
        this.userRepository = userRepository;
        this.userDTORepository = userDTORepository;
    }

    public void updateAuthenticationType(User user, AuthenticationType auth) {
        if(!user.getAuthenticationtype().equals(auth)) {
            userRepository.updateAuthenticationtype(user.getNickname(), auth);
        }
    }

    public void addNewUserUponOAuthLogin(String nickname, String email, AuthenticationType authenticationType) {
        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setEnabled(true);
        user.setCreatedtime(new Date());
        user.setAuthenticationtype(authenticationType);
        user.setPassword("");
        UserDTO userDTO=new UserDTO();
        userDTO.setNickname(nickname);
        userRepository.save(user);
        userDTORepository.save(userDTO);
    }

    public Boolean haveEmailBefore(String email){
        User user= userRepository.findByEmail(email);
        if(user==null){
            return false;
        }
        return true;
    }

    public Boolean haveNicknameBefore(String nickname){
        User user= userRepository.findByNickname(nickname);
        if(user==null){
            return false;
        }
        return true;
    }

    public void updateEnabledStatus(String nickname, boolean enabled) {
        userRepository.updateEnabledStatus(nickname, enabled);
    }

    public void updatePassword(String nickname, String password){
        userRepository.updatePassword(nickname, password);
    }

    public void updateDescription(String nickname, String description){
        userRepository.updateDescription(nickname, description);
    }

    public User updateUser(String nickname, User user) throws UserNotFoundException {
        User u= userRepository.findByNickname(nickname);
        if(u==null){
            throw new UserNotFoundException("Khong tim thay nguoi dung voi nickname: "+nickname);
        }
        if(user.getFirstname()!=null && user.getFirstname().length()>0)u.setFirstname(user.getFirstname());
        if(user.getLastname()!=null && user.getLastname().length()>0)u.setLastname(user.getLastname());
        if(user.getPassword()!=null && user.getPassword().length()>0)u.setPassword(user.getPassword());
        if(user.getDescription()!=null && user.getDescription().length()>0)u.setDescription(user.getDescription());
        return userRepository.save(u);
    }

    public User createUser(String nickname, String email, String password, String firstname, String lastname, String description){
        if(haveEmailBefore(email)){
            return null;
        }
        if(haveNicknameBefore(nickname)){
            return null;
        }
        User user =new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setRating(0d);
        user.setDescription(description);
        user.setCreatedtime(new Date());
        user.setEnabled(false);
        user.setReviews(new HashSet<>());
        user.setPosts(new HashSet<>());
        user.setComments(new HashSet<>());
        user.setLikedposts(new HashSet<>());
        user.setDislikedposts(new HashSet<>());
        user.setLikedcomments(new HashSet<>());
        user.setDislikedcomments(new HashSet<>());
        user.setLikedreviews(new HashSet<>());
        user.setDislikedreviews(new HashSet<>());
        UserDTO dto=new UserDTO();
        dto.setNickname(nickname);
        userDTORepository.save(dto);
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) throws UserNotFoundException {
        User user=userRepository.findByEmail(email);
        if(user==null){
           throw new UserNotFoundException("Khong tim thay nguoi dung voi email "+email);
        }
        return user;
    }

    public User findUserByNickname(String nickname) throws UserNotFoundException {
        User user=userRepository.findByNickname(nickname);
        if(user==null){
            throw new UserNotFoundException("Khong tim thay nguoi dung voi nickname:  "+nickname);
        }
        return user;
    }

    public void updateEnabled(String nickname, Boolean enabled) throws UserNotFoundException {
        User user=userRepository.findByNickname(nickname);
        if(user==null){
            throw new UserNotFoundException("Khong tim thay nguoi dung voi nickname:  "+nickname);
        }
        user.setEnabled(enabled);
        userRepository.save(user);
    }
}
