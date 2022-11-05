package com.assignemnt.evincedev.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class StudentDto {

    private Integer id;


    private String name;

    private String lastname;


    private String email;


    private String password;

    private String usertype;

    private Set<RoleDto> role = new HashSet<>();

    @JsonIgnore
    public String getPassword(){
        return this.password;
    }


    @JsonProperty
    public void setPassword(String password){
        this.password = password;
    }

}
