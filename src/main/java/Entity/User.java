package Entity;

public class User {
    int id;
    String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(int id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public String toString() {
        return "user {" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
