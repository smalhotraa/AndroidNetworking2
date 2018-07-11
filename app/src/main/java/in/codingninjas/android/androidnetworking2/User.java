package in.codingninjas.android.androidnetworking2;

public class User {

    String name,username,email;
    int id;

    public User(String name, String username, String email, int id) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
