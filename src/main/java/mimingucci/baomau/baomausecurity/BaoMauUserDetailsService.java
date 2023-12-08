//package mimingucci.baomau.baomausecurity;
//
//import mimingucci.baomau.entity.User;
//import mimingucci.baomau.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//
//public class BaoMauUserDetailsService implements UserDetailsService{
//    @Autowired
//    private UserRepository userRepository;
//	@Override
//	public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
//		User user = userRepository.findByNickname(nickname);
//		if (user != null) {
//			return new BaoMauUserDetails(user);
//		}
//		throw new UsernameNotFoundException("Could not find user with nickname: " + nickname);
//
//	}
//
//}
