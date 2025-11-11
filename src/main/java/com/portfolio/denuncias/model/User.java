package com.portfolio.denuncias.model;

public class User extends BaseEntity {
    private String name;
    private String email;
    private String password; // demo only
    private Role role;

    public User() {}

    public User(Long id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }

    public boolean isAdmin() { return role == Role.ADMIN; }

    @Override
    public String toString() {
        return String.format("[%d] %s <%s> (%s)", id, name, email, role);
    }
}
