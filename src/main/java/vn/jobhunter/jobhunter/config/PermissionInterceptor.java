package vn.jobhunter.jobhunter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.jobhunter.jobhunter.domain.Permission;
import vn.jobhunter.jobhunter.domain.Role;
import vn.jobhunter.jobhunter.domain.User;
import vn.jobhunter.jobhunter.service.UserService;
import vn.jobhunter.jobhunter.util.SecurityUtil;
import vn.jobhunter.jobhunter.util.error.IdInvalidException;

import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {

        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path= " + path);
        System.out.println(">>> httpMethod= " + httpMethod);
        System.out.println(">>> requestURI= " + requestURI);

        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;

        if (email != null && !email.isEmpty()) {
            User user = this.userService.handleGetUserByUserName(email);
            if (user != null) {
                Role role = user.getRole();
                if (role != null) {
                    List<Permission> permissions = role.getPermissions();

                    boolean isAllow = permissions.stream()
                            .anyMatch(p -> p.getApiPath().equals(path) && p.getMethod().equals(httpMethod));
                    // for (Permission p : permissions) {
                    // if (p.getApiPath().equals(path) && p.getMethod().equals(httpMethod)) {
                    // isAllow = true;
                    // break;
                    // }
                    // }

                    System.out.println(">>> isAllow= " + isAllow);
                    if (isAllow == false) {
                        throw new IdInvalidException("Ban khong co quyen truy cap endpoint nay");
                    }
                } else {
                    throw new IdInvalidException("Ban khong co quyen truy cap endpoint nay");

                }
            }
        }
        return true;
    }
}
