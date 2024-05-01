package com.godana.api;

import com.godana.domain.dto.user.ChangePasswordReqDTO;
import com.godana.domain.dto.user.UserResDTO;
import com.godana.domain.entity.User;
import com.godana.exception.DataInputException;
import com.godana.service.user.IUserService;
import com.godana.utils.ValidateUtils;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "") String search, Pageable pageable){
        Page<UserResDTO> userResDTOPage = iUserService.findUserBySearch(search, pageable);
        if(userResDTOPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userResDTOPage, HttpStatus.OK);
    }

    @GetMapping("/list-ban")
    public ResponseEntity<?> getAllUserBand(@RequestParam(defaultValue = "") String search, Pageable pageable){
        Page<UserResDTO> userResDTOPage = iUserService.findUserBanBySearch(search, pageable);
        if(userResDTOPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userResDTOPage, HttpStatus.OK);
    }

    @PostMapping("/ban-user/{userId}")
    public ResponseEntity<?> banUser (@PathVariable("userId") String userIdStr){
        if(!validateUtils.isNumberValid(userIdStr)){
            throw new DataInputException("UserId không hợp lệ");
        }

        Long userId = Long.parseLong(userIdStr);
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("UserId không tồn tại");
        }
        User user = userOptional.get();

        iUserService.delete(user);

        return new ResponseEntity<>("User đã bị khoá trong hệ thống",HttpStatus.OK);
    }

    @PostMapping("/unban-user/{userId}")
    public ResponseEntity<?> unanUser (@PathVariable("userId") String userIdStr){
        if(!validateUtils.isNumberValid(userIdStr)){
            throw new DataInputException("UserId không hợp lệ");
        }

        Long userId = Long.parseLong(userIdStr);
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("UserId không tồn tại");
        }
        User user = userOptional.get();

        iUserService.unban(user);

        return new ResponseEntity<>("User đã được mở trong hệ thống",HttpStatus.OK);
    }

    @PostMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword (@PathVariable("userId") String userIdStr, @ModelAttribute ChangePasswordReqDTO changePasswordReqDTO){
        if(!validateUtils.isNumberValid(userIdStr)) {
            throw new DataInputException("UserId không hợp lệ");
        }
        Long userId = Long.parseLong(userIdStr);
        Optional<User> userOptional = iUserService.findById(userId);
        if(!userOptional.isPresent()){
            throw new DataInputException("UserId không tồn tại");
        }
        User user = userOptional.get();
        String oldPassword = changePasswordReqDTO.getOldPassword();
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            String newPassword = passwordEncoder.encode(changePasswordReqDTO.getNewPassword());
            user.setPassword(newPassword);

            iUserService.save(user);

            return new ResponseEntity<>("Đổi mật khẩu thành công",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Mật khẩu cũ không đúng vui lòng xem lại", HttpStatus.BAD_REQUEST);
        }


    }
}