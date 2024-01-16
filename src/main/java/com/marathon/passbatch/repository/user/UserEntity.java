package com.marathon.passbatch.repository.user;

import java.util.Map;

import com.marathon.passbatch.repository.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="user")
//@TypeDef(name = "json", typeClass = JsonStringType.class)
public class UserEntity extends BaseEntity {

    @Id
    private String userId;

    private String userName;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private String phone;

    // json 형태로 저장되어 있는 문자열 데이터를 Map으로 매핑
    //@Type(type = "json")
//    private Map<String, Object> meta;
//
//    public String getUuid() {
//        String uuid = null;
//        if (meta.containsKey("uuid")) {
//            uuid = String.valueOf(meta.get("uuid"));
//        }
//
//        return uuid;
//    }

}
