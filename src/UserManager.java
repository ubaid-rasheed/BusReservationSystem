public class UserManager {
    private User[] users = new User[100];
    private int count = 0;

    public String loginOrSignup(String username, String password) {
        for (int i = 0; i < count; i++) {
            if (users[i].username.equals(username)) {
                if (users[i].password.equals(password)) {
                    return "LOGIN_SUCCESS";
                } else {
                    return "WRONG_PASSWORD";
                }
            }
        }

        users[count++] = new User(username, password);
        return "USER_CREATED";
    }
}
