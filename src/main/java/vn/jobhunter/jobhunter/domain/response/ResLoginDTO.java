package vn.jobhunter.jobhunter.domain.response;

import vn.jobhunter.jobhunter.domain.Role;

public class ResLoginDTO {
    private String accessToken;
    private UserLogin user;

    public static class UserLogin {
        private String email;
        private String name;
        private long id;
        private Role role;

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

    }

    public static class UserInsideToken
    {
        private long id;
        private String email;
        private String name;
        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserLogin getUser() {
        return user;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }
}
