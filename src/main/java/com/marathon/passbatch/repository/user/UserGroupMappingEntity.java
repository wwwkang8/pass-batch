package com.marathon.passbatch.repository.user;


import com.marathon.passbatch.repository.BaseEntity;
import jakarta.persistence.Entity;
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
@IdClass(UserGroupMappingId.class)
public class UserGroupMappingEntity extends BaseEntity {

    /**
     * 사용자가 속한 사용자그룹을 보여주는 엔티티
     * 이 엔티티는 사용자들을 그룹화 시켜 관리하기 위한 엔티티이다.
     *
     * 복합키 선언 : @IdClass 어노테이션 선언을 하여 복합키를 설정한다.
     * */

    @Id
    private String userGroupId;
    @Id
    private String userId;

    private String userGroupName;
    private String description;

}
