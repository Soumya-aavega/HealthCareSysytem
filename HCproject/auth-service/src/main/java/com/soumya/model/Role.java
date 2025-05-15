package com.soumya.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "roles")
@Data
public class Role {

  private String id;
  private UserRole name;

}
