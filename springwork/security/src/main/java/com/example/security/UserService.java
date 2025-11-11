package com.example.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		Map<String, Object> user = sqlSession.selectOne("user.detail", userid);
		if(user == null)
			throw new UsernameNotFoundException(userid);
		List<GrantedAuthority> authority = new ArrayList<>();
		authority.add(new SimpleGrantedAuthority(user.get("AUTHORITY").toString()));
		return new UserDTO(user.get("USERNAME").toString(), user.get("PASSWORD").toString(), (Integer) Integer.valueOf(user.get("ENABLED").toString()) == 1, true, true, true, authority, user.get("USERNAME").toString());
	}

}
