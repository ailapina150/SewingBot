import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonsCreator {

    public static InlineKeyboardMarkup createButtonRows(List<List<String>> buttonsName) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        for (List<String> stringList : buttonsName) {
            List<InlineKeyboardButton> rowInline = stringList
                    .stream()
                    .map(s -> {
                        InlineKeyboardButton button = new InlineKeyboardButton();
                        button.setText(s);
                        button.setCallbackData(s);
                        return button;
                    })
                    .toList();
            rowsInLine.add(rowInline);
        }
        inlineKeyboardMarkup.setKeyboard(rowsInLine);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createButton(List<String> buttonsName) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        buttonsName.forEach(s -> {
            var button = new InlineKeyboardButton();
            button.setText(s);
            button.setCallbackData(s);
            rowInline.add(button);
        });
        rowsInLine.add(rowInline);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);

        return inlineKeyboardMarkup;
    }

}
