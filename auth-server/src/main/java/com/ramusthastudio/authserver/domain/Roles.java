package com.ramusthastudio.authserver.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "s_roles")
public class Roles implements Serializable {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  private String name;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "s_permissions_roles", joinColumns = {
      @JoinColumn(name = "id_role", referencedColumnName = "id")
  },
      inverseJoinColumns = {
          @JoinColumn(name = "id_permission", referencedColumnName = "id")
      })
  private List<Permissions> permissions;
}
