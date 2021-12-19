package com.hz.common.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 角色信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDto implements Serializable {
    private Long id;
    private String code;
    private String name;
    private List<String> urls;
}
