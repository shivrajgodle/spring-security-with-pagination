package com.assignemnt.evincedev.services;

import com.assignemnt.evincedev.payload.StudentDto;
import com.assignemnt.evincedev.payload.StudentResponce;


public interface StudentService {


    StudentDto registerNewStudent(StudentDto studentDto);
    StudentDto createStudent(StudentDto studentdto);
    StudentDto updateStudent(StudentDto studentdto, Integer studentId);
    StudentDto getStudentById(Integer studentId);
    StudentResponce getAllStudents(Integer pageNumber , Integer pageSize , String sortBy , String sortDir);
	

    void deleteStudent(Integer studentId);
}
