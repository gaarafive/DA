package com.example.karaoke.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.karaoke.Repository.RoleRepository;
import com.example.karaoke.Repository.UserRepository;
import com.example.karaoke.dto.UserDTO;
import com.example.karaoke.entity.Role;
import com.example.karaoke.entity.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired 
	private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User account = repo.findByUsername(username);
		if(account==null)
			throw new UsernameNotFoundException("User 404");
//		GrantedAuthority authority = new SimpleGrantedAuthority(account.getRole().getName());
//		UserDetails userDetails = (UserDetails)new org.springframework.security.core.userdetails.User(account.getUsername(), 
//				account.getPassword(), Arrays.asList(authority));
//		return userDetails;
		return new UserPrincipal(account);
	}
	
	
	public User registerNewUserAccount(UserDTO userdto) {
        User account = new User();
        account.setName(userdto.getName());
        account.setUsername(userdto.getUsername());
        account.setTelephonenumber(userdto.getTelephonenumber());
        account.setPassword(passwordEncoder.encode(userdto.getPassword()));
        Role roleuser = roleRepository.findByName(userdto.getRole());
        account.setRole(roleuser);
        account.setAddress(userdto.getAddress());
        return repo.save(account);
    }
	
	public User findByUsername(String username) {
		return repo.findByUsername(username);
	}
	
	public boolean hasRole(String role) {
		  Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
		  SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		  boolean hasRole = false;
		  for (GrantedAuthority authority : authorities) {
		     hasRole = authority.getAuthority().equals(role);
		     if (hasRole) {
			  break;
		     }
		  }
		  return hasRole;
	}  
	public List<User> getAll() {
		return repo.findAll();
	}
	public User save(User user) {
		return repo.save(user);
	}
	public void remove(int id) {
		repo.deleteById(id);
	}
	public User getById(int id) {
		Optional<User> oUser = repo.findById(id);
		if(oUser.isPresent()) {
			return oUser.get();
		}
		return null;
	}
	public Role getRoleByName(String name) {
		Role Role = roleRepository.findByName(name);
		
		return Role;
	}
}