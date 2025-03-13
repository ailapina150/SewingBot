import java.util.List;

public enum ActButton {
    START("Перезапустить", "Перезапустить", "/start"),
    ORDER("Каталог изделий", "Выберите издения", "/catalog"),
    DONAT("Каталог изделий", "Выберите издения", "/donat"),
    INTERESTING("Интересно", "Интересно", "/interesting"),
    NOT_INTERESTING("Не интресно", "не интересно", "/notInteresting"),
    YES("Да","да", "/yes"),
    NO("Нет","нет", "/no");

    private final String name;
    private final String replay;
    private final String command;

    ActButton(String name, String replay, String command) {
        this.name = name;
        this.replay = replay;
        this.command = command;
    }

    public String getReplay() {
        return replay;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public static List<String> getStartButton() {
        return List.of(ActButton.INTERESTING.getName(),
                ActButton.NOT_INTERESTING.getName());
    }

    public static List<String> getSendButton() {
        return List.of(ActButton.YES.getName(),
                ActButton.NO.getName());
    }

}
