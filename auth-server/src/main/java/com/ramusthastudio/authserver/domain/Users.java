package com.ramusthastudio.authserver.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "s_users")
public class Users implements UserDetails {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  private String username;
  private String email;
  private boolean enabled;
  private boolean isAccountNonExpired;
  private boolean isCredentialsNonExpired;
  private boolean isAccountNonLocked;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "s_users_roles", joinColumns = {
      @JoinColumn(name = "id_user", referencedColumnName = "id")
  },
      inverseJoinColumns = {
          @JoinColumn(name = "id_role", referencedColumnName = "id")
      })
  private List<Roles> roles;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinTable(name = "s_users_passwords", joinColumns = {
      @JoinColumn(name = "id_user", referencedColumnName = "id")
  },
      inverseJoinColumns = {
          @JoinColumn(name = "id_password", referencedColumnName = "id")
      })
  private Passwords passwords;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    HashSet<GrantedAuthority> authorities = new HashSet<>();
    roles.forEach(aRoles -> {
      authorities.add(new SimpleGrantedAuthority(aRoles.getName()));
      aRoles.getPermissions().forEach(aPermissions ->
          authorities.add(new SimpleGrantedAuthority(aPermissions.getValue())));
    });
    return authorities;
  }

  @Override
  public String getPassword() {
    return passwords.getCurrentPassword();
  }
}
