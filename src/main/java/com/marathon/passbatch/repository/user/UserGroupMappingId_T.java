package com.marathon.passbatch.repository.user;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class UserGroupMappingId_T implements Serializable {

    private Integer userGroupId;
    private Integer userId;

}
