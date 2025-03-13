import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ButtonsCreator {

    public static InlineKeyboardMarkup createButtonRows(@NotNull List<List<String>> buttonsName) {
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

    public static InlineKeyboardMarkup createButton(@NotNull List<String> buttonsName) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        if(buttonsName.isEmpty()){
            inlineKeyboardMarkup.setKeyboard(rowsInLine);
            return inlineKeyboardMarkup;
        }
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
