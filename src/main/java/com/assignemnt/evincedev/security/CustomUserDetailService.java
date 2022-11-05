package com.assignemnt.evincedev.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assignemnt.evincedev.entities.Student;
import com.assignemnt.evincedev.exceptions.ResourceNotFoundException;
import com.assignemnt.evincedev.repositories.StudentRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private StudentRepo studentRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Student student = this.studentRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("Student", "email:"+username,0 ));
		return student;
	}
	
	
}
