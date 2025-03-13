public class BotUser {
    private final Long userId;
    private String userName;
    private String firstName;
    private String modelName;

    public BotUser(Long userId, String firstName) {
        this.userId = userId;
        this.userName = "";
        this.firstName = firstName;
        this.modelName = "";
    }

    public BotUser(Long userId) {
        this.userId = userId;
        this.userName = "";
        this.firstName = "";
        this.modelName = "";
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getModelName() {
        return modelName;
    }
}
