package com.gabrielmiguelpedro.maclarenapp;


public class User {

    private String email;
    private int lastCode;
    private boolean permission_location;
    private boolean permission_camera;

    public User(String email, int lastCode, boolean permission_location) {
        this.email = email;
        this.lastCode = lastCode;
        this.permission_location = permission_location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLastCode() {
        return lastCode;
    }

    public void setLastCode(int lastCode) {
        this.lastCode = lastCode;
    }

    public boolean isPermission_location() {
        return permission_location;
    }

    public void setPermission_location(boolean permission_location) {
        this.permission_location = permission_location;
    }

    public boolean isPermission_camera() {
        return permission_camera;
    }

    public void setPermission_camera(boolean permission_camera) {
        this.permission_camera = permission_camera;
    }
}
