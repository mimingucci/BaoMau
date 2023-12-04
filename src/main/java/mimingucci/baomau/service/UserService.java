package mimingucci.baomau.service;

import mimingucci.baomau.entity.AuthenticationType;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(int id) throws UserNotFoundException {
        User user = userRepository.findById(id).get();
        if(user==null){
            throw new UserNotFoundException("Khong the tim duoc nguoi dung voi id "+id);
        }else {
            return user;
        }
    }

    public User findUserById(int id) throws UserNotFoundException {
        User user=userRepository.findById(id).get();
        if(user==null){
            throw new UserNotFoundException("Khong tim thay nguoi dung voi id "+id);
        }
        return user;
    }

    public void updateAuthenticationType(User user, AuthenticationType auth) {
        if(!user.getAuthenticationtype().equals(auth)) {
            userRepository.updateAuthenticationtype(user.getId(), auth);
        }
    }

    public void addNewUserUponOAuthLogin(String name, String email, AuthenticationType authenticationType) {
        User user = new User();
        user.setEmail(email);
        setName(name, user);
        user.setEnabled(true);
        user.setCreatedtime(new Date());
        user.setAuthenticationtype(authenticationType);
        user.setPassword("");
        userRepository.save(user);
    }

    private void setName(String name, User user) {
        String[] nameArray = name.split(" ");
        if (nameArray.length < 2) {
            user.setFirstname(name);
            user.setLastname("");
        } else {
            String firstName = nameArray[0];
            user.setFirstname(firstName);

            String lastName = name.replaceFirst(firstName + " ", "");
            user.setLastname(lastName);
        }
    }

    public Boolean haveEmailBefore(String email){
        User user= userRepository.findByEmail(email);
        if(user==null){
            return false;
        }
        return true;
    }

    public void updateEnabledStatus(int id, boolean enabled) {
        userRepository.updateEnabledStatus(id, enabled);
    }

    public void updatePassword(int id, String password){
        userRepository.updatePassword(id, password);
    }

    public void updateDescription(int id, String description){
        userRepository.updateDescription(id, description);
    }

    public User updateUser(int id, User user) throws UserNotFoundException {
        User u= userRepository.findById(id).get();
        if(u==null){
            throw new UserNotFoundException("Khong tim thay nguoi dung voi id: "+id);
        }
        if(user.getFirstname()!=null && user.getFirstname().length()>0)u.setFirstname(user.getFirstname());
        if(user.getLastname()!=null && user.getLastname().length()>0)u.setLastname(user.getLastname());
        if(user.getPassword()!=null && user.getPassword().length()>0)u.setPassword(user.getPassword());
        if(user.getDescription()!=null && user.getDescription().length()>0)u.setDescription(user.getDescription());
        return userRepository.save(u);
    }

    public User createUser(String email, String password, String firstname, String lastname, String description){
        if(haveEmailBefore(email)){
            return null;
        }
        User user =new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setDescription(description);
        user.setCreatedtime(new Date());
        user.setEnabled(false);
        user.setReviews(new ArrayList<>());
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) throws UserNotFoundException {
        User user=userRepository.findByEmail(email);
        if(user==null){
           throw new UserNotFoundException("Khong tim thay nguoi dung voi email "+email);
        }
        return user;
    }
}
