package com.marathon.passbatch.repository.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_group_mapping")
@IdClass(UserGroupMappingId_T.class)
public class UserGroupMappingEntity_T {

    @Id
    private Integer userGroupId;

    @Id
    private Integer userId;

    private String userGroupName;
    private String description;

}
