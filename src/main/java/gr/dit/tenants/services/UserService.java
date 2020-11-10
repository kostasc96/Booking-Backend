package gr.dit.tenants.services;

import gr.dit.tenants.entities.Role;
import gr.dit.tenants.exceptions.BadRequestException;
import gr.dit.tenants.util.CustomBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import gr.dit.tenants.entities.User;
import gr.dit.tenants.payload.user.UserUpdateBody;
import gr.dit.tenants.repositories.UserRepository;

import javax.transaction.Transactional;

@Service
public class UserService
{
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Transactional
    public User updateUser(long userId, UserUpdateBody body)
    {
        User user = userRepository.findById(userId).get();

        if(body.getIsHost() == false &&
           user.getRoles().contains(Role.HOST))
            throw new BadRequestException("Cannot unset host property once it is set");

        if(body.getIsHost() == true)
            user.getRoles().add(Role.HOST);

        CustomBeanUtils.copyNonNullProperties(body,user,"isHost");
        return userRepository.saveAndFlush(user);
    }


}
