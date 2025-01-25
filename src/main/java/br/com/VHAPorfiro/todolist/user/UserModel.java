package br.com.VHAPorfiro.todolist.user;

public class UserModel {

    private String username;
    private String name;
    private String password;

    public UserModel(){}

    public UserModel(String name, String password, String username) {
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
