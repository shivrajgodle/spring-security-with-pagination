package com.assignemnt.evincedev.services.impl;


import com.assignemnt.evincedev.config.AppConstants;
import com.assignemnt.evincedev.entities.Role;
import com.assignemnt.evincedev.entities.Student;
import com.assignemnt.evincedev.exceptions.ResourceNotFoundException;
import com.assignemnt.evincedev.payload.StudentDto;
import com.assignemnt.evincedev.payload.StudentResponce;
import com.assignemnt.evincedev.repositories.RoleRepo;
import com.assignemnt.evincedev.repositories.StudentRepo;
import com.assignemnt.evincedev.services.StudentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class StudentServiceImpl implements StudentService {



    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;
    
    @Override
    public StudentDto registerNewStudent(StudentDto studentDto) {


        System.out.println("Before final password is:-"+studentDto.getPassword()+"email is:-"+studentDto.getEmail());




        Student student = modelMapper.map(studentDto, Student.class);

        //encode password
        student.setPassword(this.passwordEncoder.encode(student.getPassword()));

       // String password = this.passwordEncoder.encode(studentDto.getPassword());

        //System.out.println("encrypted pwd is:-"+password);

      //  student.setPassword(studentDto.getPassword());



        //encode password
    	//student.setPassword(password);

        System.out.println("after final password is:-"+student.getPassword()+"email is:-"+student.getEmail());




        Role role = this.roleRepo.findById(AppConstants.ADMIN_USER ).get();

        student.getRole().add(role);
    	Student newStudent = this.studentRepo.save(student);

		return this.modelMapper.map( newStudent , StudentDto.class);
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = this.dtoToStudent(studentDto);
        Student savedStudent = studentRepo.save(student);
        return this.studentToDto(savedStudent);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto, Integer studentId) {


        Student student = studentRepo.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student","Id",studentId));

        student.setName(studentDto.getName());
        student.setLastname(studentDto.getLastname());
        student.setEmail(studentDto.getEmail());
        student.setPassword(studentDto.getPassword());
        student.setUsertype(studentDto.getUsertype());


        Student updatedStudent = studentRepo.save(student);
        StudentDto studentDto2 =this.studentToDto(updatedStudent);


        return studentDto2;

    }

    @Override
    public StudentDto getStudentById(Integer studentId) {
    	Student student = studentRepo.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student", "studentId", studentId));
		return this.studentToDto(student);
    }



	
	public StudentResponce getAllStudents(Integer pageNumber , Integer pageSize , String sortBy , String sortDir) {
		// TODO Auto-generated method stub
		
		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Student> pageStudent = studentRepo.findAll(p);
		
		List<Student> allStudents = pageStudent.getContent();
		
		List<StudentDto> studentDtos = allStudents.stream().map(student -> this.modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());
		
		StudentResponce studentResponce = new StudentResponce();
		studentResponce.setContent(studentDtos);
		studentResponce.setPageNumber(pageStudent.getNumber());
		studentResponce.setPageSize(pageStudent.getSize());
		studentResponce.setTotalElements(pageStudent.getTotalElements());
		studentResponce.setTotalPages(pageStudent.getTotalPages());
		studentResponce.setLastPage(pageStudent.isLast());
		
		return studentResponce;
	}
    

    @Override
    public void deleteStudent(Integer studentId) {

    	Student student = studentRepo.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student", "Id", studentId));
		
		studentRepo.delete(student);

    }


    public Student dtoToStudent(StudentDto studentDto) {

        Student student = this.modelMapper.map(studentDto, Student.class);
        return student;

    }

    public StudentDto studentToDto(Student student) {

        StudentDto studentDto = this.modelMapper.map(student, StudentDto.class);
        return studentDto;

    }

	


}
