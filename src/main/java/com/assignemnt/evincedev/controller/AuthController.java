package com.assignemnt.evincedev.controller;


import com.assignemnt.evincedev.entities.Student;
import com.assignemnt.evincedev.exceptions.ApiException;
import com.assignemnt.evincedev.payload.JwtAuthRequest;
import com.assignemnt.evincedev.payload.JwtAuthResponce;
import com.assignemnt.evincedev.payload.StudentDto;
import com.assignemnt.evincedev.security.JwtTokenHelper;
import com.assignemnt.evincedev.services.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private StudentService studentService;

	@Autowired
	private ModelMapper mapper;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponce> createToken(@RequestBody JwtAuthRequest request) throws Exception{
	
		this.authenticate(request.getUsername(),request.getPassword());

		 UserDetails userDetails  = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponce responce = new JwtAuthResponce();
		responce.setToken(token);
		
		return new ResponseEntity<JwtAuthResponce>(responce, HttpStatus.OK);
	}


	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		}catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new ApiException("invalid username or password !!!");
		}
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<StudentDto> registerUser(@RequestBody StudentDto studentDto){
		System.out.println("1");
		System.out.println("2"+studentDto.getEmail());
		System.out.println("3"+studentDto.getPassword());

		StudentDto registeredStudent = null;

		try{
			 registeredStudent = this.studentService.registerNewStudent(studentDto);
		}
		catch (BadCredentialsException e){
			throw new ApiException("invalid username or password !!!");
		}

		System.out.println("4"+registeredStudent.getPassword());
		return new ResponseEntity<StudentDto>(registeredStudent,HttpStatus.CREATED);
	}

	

}
