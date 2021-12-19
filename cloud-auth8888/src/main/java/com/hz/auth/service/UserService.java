package com.hz.auth.service;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.common.entity.UserDTO;
import com.hz.auth.util.AESUtil;
import com.hz.common.auth.RoleDto;
import com.hz.common.auth.SecurityUser;
import com.hz.common.auth.UserDto;
import com.hz.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理业务类
 * 实现Spring Security的UserDetailsService接口，用于加载用户信息
 */
@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /*@Autowired
    private JdbcTemplate jdbcTemplate;*/

    private List<UserDto> userList;


    /**
     * 刷新角色资源权限数据存入redis
     * 用来方便gateway鉴权认证时使用
     * 这里可以使用mysql相关操作
     */
    public void refreshAllRoleUrls(){

        //本地未绑定数据库
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new UserDto("1","macro", password,1, CollUtil.toList(new RoleDto())));
        userList.add(new UserDto("2","andy", password,1,CollUtil.toList(new RoleDto())));

        //绑定数据库相关
        /*List<RoleDto> roleList= jdbcTemplate.query("select DISTINCT id,role_id code,name from role where status=1 and name like '%商城%'",new BeanPropertyRowMapper<RoleDto>(RoleDto.class));
        if(CollectionUtils.isNotEmpty(roleList)){
            for (RoleDto role:roleList) {
                role.setUrls(jdbcTemplate.queryForList("select distinct url from permission where status=1 and type=2 and permission_id in(select permission_id from role_permission where role_id in('"+role.getCode()+"'))",String.class));
            }
        }
        redisTemplate.opsForValue().set(Constant.PERMISSION_ROLES_ALL_KEY, JSON.toJSONString(roleList, SerializerFeature.WriteMapNullValue));*/
    }

    /**
     * 初始化读取角色资源权限数据
     * 存入redis方便gateway鉴权使用
     */
    @PostConstruct
    public void initData() {
        refreshAllRoleUrls();
    }

    /**
     * 系统采用pb-cms后台数据作为权限，
     * 增加一个oauth_password存储,此数据在amdin维护
     * 原始密码的AES密文，方便在oauth授权时
     * 解析密码,以及用户权限数据，此处只查询商城角色权限
     * 商城微服务角色名字都必须含有商城，仅查询商城权限
     * @param username
     * @return
     * @throws
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //绑定数据库相关
        /*UserDto userDto = jdbcTemplate.queryForObject("select distinct  user_id 'userId',username,oauth_password 'password',status from user where status=1 and username='"+username+"'",new BeanPropertyRowMapper<UserDto>(UserDto.class));
        List<RoleDto> userRoles = jdbcTemplate.query("select distinct id,role_id code,name from role where status=1 and name like '%商城%'and role_id in(select ur.role_id from user_role ur,user u where  u.user_id=ur.user_id and u.status=1 and u.username='"+username+"')",new BeanPropertyRowMapper<RoleDto>(RoleDto.class));
        if (ObjectUtils.isEmpty(userDto)) {
            throw new UsernameNotFoundException(Constant.USERNAME_PASSWORD_ERROR);
        }
        if(CollectionUtils.isNotEmpty(userRoles)){
            for (RoleDto role:userRoles) {
                 role.setUrls(jdbcTemplate.queryForList("select distinct url from permission where status=1 and type=2 and permission_id in(select permission_id from role_permission where role_id in('"+role.getCode()+"'))",String.class));
            }
        }
        userDto.setPassword(passwordEncoder.encode(AESUtil.decrypt(userDto.getPassword(),null)));
        userDto.setRoles(userRoles);
        SecurityUser securityUser = new SecurityUser(userDto);
        if (!securityUser.isEnabled()) {
            throw new DisabledException(Constant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(Constant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(Constant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(Constant.CREDENTIALS_EXPIRED);
        }*/

        //未绑定数据库
        List<UserDto> findUserList = userList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (CollUtil.isEmpty(findUserList)) {
            throw new UsernameNotFoundException(Constant.USERNAME_PASSWORD_ERROR);
        }
        SecurityUser securityUser = new SecurityUser(findUserList.get(0));
        if (!securityUser.isEnabled()) {
            throw new DisabledException(Constant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(Constant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(Constant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(Constant.CREDENTIALS_EXPIRED);
        }


        return securityUser;
    }

}
