package com.ramusthastudio.authserver.publicapi;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPublic implements Serializable {
  private String username;
  private String password;
  private String email;
}
