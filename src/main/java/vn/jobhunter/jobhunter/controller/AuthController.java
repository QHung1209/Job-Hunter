package vn.jobhunter.jobhunter.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.jobhunter.jobhunter.domain.User;
import vn.jobhunter.jobhunter.domain.request.ReqLoginDTO;
import vn.jobhunter.jobhunter.domain.response.ResLoginDTO;
import vn.jobhunter.jobhunter.service.UserService;
import vn.jobhunter.jobhunter.util.SecurityUtil;
import vn.jobhunter.jobhunter.util.error.IdInvalidException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final SecurityUtil securityUtil;

    private final UserService userService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
            UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @Value("${time.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    @PostMapping("/auth/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody ReqLoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // create a token

        User currentUser = this.userService.handleGetUserByUserName(loginDTO.getUsername());

        ResLoginDTO res = new ResLoginDTO();
        if (currentUser != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
            userLogin.setEmail(currentUser.getEmail());
            userLogin.setId(currentUser.getId());
            userLogin.setName(currentUser.getName());
            userLogin.setRole(currentUser.getRole());
            res.setUser(userLogin);
        }
        String access_token = this.securityUtil.createAccessToken(authentication.getName(), res);

        res.setAccessToken(access_token);

        String refresh_token = this.securityUtil.createRefeshToken(loginDTO.getUsername(), res);
        this.userService.updateUserToken(refresh_token, loginDTO.getUsername());

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(res);
    }

    @GetMapping("/auth/account")
    public ResponseEntity<ResLoginDTO.UserLogin> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";

        User currentUser = this.userService.handleGetUserByUserName(email);

        ResLoginDTO res = new ResLoginDTO();
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();

        if (currentUser != null) {

            userLogin.setEmail(currentUser.getEmail());
            userLogin.setId(currentUser.getId());
            userLogin.setName(currentUser.getName());
            userLogin.setRole(currentUser.getRole());
            res.setUser(userLogin);
        }
        return ResponseEntity.ok().body(userLogin);
    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<ResLoginDTO> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "abc") String refresh_token) throws IdInvalidException {

        if (refresh_token.equals("abc")) {
            throw new IdInvalidException("Khong co refresh token o cookie");
        }
        Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refresh_token);
        String email = decodedToken.getSubject();

        User currentUser = this.userService.getUserByRefreshTokenAndEmail(refresh_token, email);

        if (currentUser == null) {
            throw new IdInvalidException("Refesh token khong hop le");
        }

        ResLoginDTO res = new ResLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUserName(email);
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();

        if (currentUserDB != null) {
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setId(currentUserDB.getId());
            userLogin.setName(currentUserDB.getName());
            userLogin.setRole(currentUserDB.getRole());
            res.setUser(userLogin);
        }
        String access_token = this.securityUtil.createAccessToken(email, res);

        res.setAccessToken(access_token);

        String new_refresh_token = this.securityUtil.createRefeshToken(email, res);
        this.userService.updateUserToken(new_refresh_token, email);

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", new_refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(res);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout() throws IdInvalidException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        if (email.equals("")) {
            throw new IdInvalidException("AcessToken khong hop le");
        }
        this.userService.updateUserToken(null, email);
        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok(null);
    }

}
