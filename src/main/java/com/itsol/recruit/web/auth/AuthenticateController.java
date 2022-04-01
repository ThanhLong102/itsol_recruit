package com.itsol.recruit.web.auth;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.UserDTO;
import com.itsol.recruit.entity.OTP;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.security.jwt.JWTFilter;
import com.itsol.recruit.security.jwt.TokenProvider;
import com.itsol.recruit.service.AuthenticateService;
import com.itsol.recruit.service.OtpService;
import com.itsol.recruit.service.UserService;
import com.itsol.recruit.web.vm.LoginVM;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(value = Constants.Api.Path.AUTH)
@Api(tags = "Auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticateController {

    private final AuthenticateService authenticateService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final OtpService otpService;

    public AuthenticateController(
            AuthenticateService authenticateService, AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService, TokenProvider tokenProvider,OtpService otpService) {
        this.authenticateService = authenticateService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.otpService=otpService;
    }

    @PostMapping(Constants.Api.Path.Account.REGISTER)
    public ResponseEntity<Boolean> register(@Valid @RequestBody UserDTO dto) {

        return ResponseEntity.ok().body(authenticateService.signup(dto));
    }

    @GetMapping(Constants.Api.Path.Account.ACTIVE_ACCOUNT)
    public ResponseEntity<Boolean> activeAccount( @RequestParam("otp") String otp , @RequestParam("id") Long userId ) {
        return ResponseEntity.ok().body(authenticateService.activeAccount(otp,userId));
    }

    @PostMapping(Constants.Api.Path.Account.RESET_PASSWORD_FINISH)
    public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok().body(authenticateService.changePassword(dto));
    }

    @PostMapping(Constants.Api.Path.Account.RESET_PASSWORD_INIT)
    public ResponseEntity<Boolean> resetPasswordInit(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok().body(otpService.sendOtp(dto));
    }

    @PostMapping( Constants.Api.Path.Auth.LOGIN)
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginVM loginVM) {
//		Tạo chuỗi authentication từ username và password (object LoginRequest
//		- file này chỉ là 1 class bình thường, chứa 2 trường username và password)

        UsernamePasswordAuthenticationToken authenticationString = new UsernamePasswordAuthenticationToken(
                loginVM.getUserName(),
                loginVM.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationString);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, loginVM.getRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, String.format("Bearer %s", jwt));
        return new ResponseEntity<>(Collections.singletonMap("token", jwt), httpHeaders, HttpStatus.OK); //Trả về chuỗi jwt(authentication string)

//        User userLogin = userService.findUserByUserName(adminLoginVM.getUserName());
//        return ResponseEntity.ok().body(new JWTTokenResponse(jwt, userLogin.getUserName())); //Trả về chuỗi jwt(authentication string)

    }

}
