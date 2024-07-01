package com.degerli.authentication.service;


import com.degerli.authentication.model.MyUser;
import com.degerli.authentication.repository.MyUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

  private MyUserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<MyUser> user = repository.findByUsername(username);
    if (user.isPresent()) {
      var userObj = user.get();
      return User.builder()
          .username(userObj.getUsername())
          .password(userObj.getPassword())
          .roles(getRoles(userObj))
          .build();
    } else {
      throw new UsernameNotFoundException(username);
    }
  }

  private String[] getRoles(MyUser user) {
    if (user.getRole() == null) {
      return new String[]{"USER"};
    }
    return user.getRole().split(",");
  }
}