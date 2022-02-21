package com.hz.service.impl;

import com.common.entity.MailConstants;
import com.hz.dao.EmailDao;
import com.hz.dao.UserDAO;
import com.hz.service.UserService;
import com.common.entity.Email;
import com.common.entity.User;
import com.common.entity.UserRoles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("userService")
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmailDao emailDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public int save(User user, UserRoles userRoles) {
        //user.setId(UUID.randomUUID().toString().replace("-",""));
        user.setId(0);
        if (userRoles!=null){
            userRoles.setId(0);
            userDAO.addUserRoles(userRoles);
            Email email = new Email();
            email.setEmailId(0);
            email.setEmail(user.getName());
            email.setStatus(2);
            int i = emailDao.addEmailMessage(email);

            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_QUEUE_NAME,email, new CorrelationData(String.valueOf(email.getEmailId())));


            log.info("i: {}",i);
        }

        return userDAO.save(user);
    }

    @Override
    public int addUserRoles(UserRoles userRoles) {
        userRoles.setRoleId(0);

        return userDAO.addUserRoles(userRoles);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User getUser(String username) {

        return userDAO.getUser(username);
        //throw new RuntimeException("认证失败");
    }

    @Override
    public User getUserByUserId(String userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public User findRolesByUsername(String username) {
        return userDAO.findRolesByUsername(username);
    }

    @Override
    public int deleteUser(String userId, String password) {
        return userDAO.deleteUserByOwner(userId, password);
    }

    @Override
    public int deleteUser(String userId) {
        return userDAO.deleteUser(userId);
    }

    @Override
    public int updateUserPassword(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public String createQrImg() {
        return UUID.randomUUID().toString().replace("-","").trim();
    }

}
