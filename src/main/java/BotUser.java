import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
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

}
