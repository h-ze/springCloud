package com.hz.common.auth;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private String userId;
    private String username;
    private String password;
    private Integer status;
    private List<RoleDto> roles;

}
