package com.marathon.passbatch.repository.user;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserGroupMappingId implements Serializable {

    /**
     * 복합키 클래스를 만들기 위해서는 Serializable을 구현해야한다.
     * */

    private String userGroupId;
    private String userId;

}
