package com.assignemnt.evincedev.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
public class Role {

    @Id
    private int id;
    private String name;
}
