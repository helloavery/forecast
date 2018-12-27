package com.itavery.forecast.assemblers;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  10/9/18            
 |            
 *===========================================================================*/

import com.itavery.forecast.mithra.user.UsersDB;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    public UserDTO covertToDTO(UsersDB userDB) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userDB.getUserId());
        userDTO.setAuthyUserId(userDB.getAuthyUserId());
        userDTO.setFirstName(userDB.getFirstName());
        userDTO.setLastName(userDB.getLastName());
        userDTO.setUsername(userDB.getUsername());
        userDTO.setEmail(userDB.getEmail());
        userDTO.setCountryCode(userDB.getCountryCode());
        userDTO.setPhoneNumber(userDB.getPhoneNumber());
        userDTO.setRegion(userDB.getRegion());
        return userDTO;
    }

    public UsersDB covertToDB(UserDTO userDTO) {
        UsersDB usersDB = new UsersDB();
        usersDB.setFirstName(userDTO.getFirstName());
        usersDB.setLastName(userDTO.getLastName());
        usersDB.setUsername(userDTO.getUsername());
        usersDB.setEmail(userDTO.getEmail());
        usersDB.setCountryCode(userDTO.getCountryCode());
        usersDB.setPhoneNumber(userDTO.getPhoneNumber());
        usersDB.setRegion(userDTO.getRegion());
        return usersDB;
    }

    public UsersDB covertToDB(RegistrationDTO registrationDTO) {
        UsersDB usersDB = new UsersDB();
        usersDB.setFirstName(registrationDTO.getFirstName());
        usersDB.setLastName(registrationDTO.getLastName());
        usersDB.setUsername(registrationDTO.getUsername());
        usersDB.setEmail(registrationDTO.getEmail());
        usersDB.setCountryCode(registrationDTO.getCountryCode());
        usersDB.setPhoneNumber(registrationDTO.getPhoneNumber());
        return usersDB;
    }
}
