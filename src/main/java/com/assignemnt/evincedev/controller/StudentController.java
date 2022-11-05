package com.assignemnt.evincedev.controller;

import com.assignemnt.evincedev.config.AppConstants;
import com.assignemnt.evincedev.exceptions.ApiException;
import com.assignemnt.evincedev.payload.ApiResponce;
import com.assignemnt.evincedev.payload.StudentDto;
import com.assignemnt.evincedev.payload.StudentResponce;
import com.assignemnt.evincedev.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto createStudentDto = studentService.createStudent(studentDto);
        return new ResponseEntity<StudentDto>(createStudentDto, HttpStatus.CREATED);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@Valid @RequestBody StudentDto studentDto, @PathVariable("studentId") Integer sid) {

        StudentDto updateStudentDto = studentService.updateStudent(studentDto, sid);

        return ResponseEntity.ok(updateStudentDto);
    }
    
    @DeleteMapping("/{studentId}")
	public ResponseEntity<ApiResponce> deleteStudent(@PathVariable("studentId") Integer sid) {

    	studentService.deleteStudent(sid);
		return new ResponseEntity<ApiResponce>(new ApiResponce("Student Deleted Successfully", true), HttpStatus.OK);
	}

    
    @GetMapping("/pagination")
	public ResponseEntity<StudentResponce> getAllStudents(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			
			){
    	StudentResponce studentResponce = this.studentService.getAllStudents(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<StudentResponce>(studentResponce,HttpStatus.OK); 
	}



	@GetMapping("/{studentId}")
	public ResponseEntity<StudentDto> getSingleUser(@PathVariable Integer studentId) {

		return ResponseEntity.ok(studentService.getStudentById(studentId) );
	}



}
