import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j
public abstract class TelegramSender extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return AppProperties.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return AppProperties.BOT_TOKEN;
    }

    public long getChatId(Update update) {
        return update.hasCallbackQuery()
                ? update.getCallbackQuery().getMessage().getChatId()
                : update.getMessage().getChatId();
    }

    public Message getMessage(Update update) {
        return update.hasCallbackQuery()
                ? update.getCallbackQuery().getMessage()
                : update.getMessage();
    }

    public String getButtonData(Update update) {
        return update.hasCallbackQuery()
                ? update.getCallbackQuery().getData()
                : update.getMessage().hasText()
                ? update.getMessage().getText().trim()
                : "";
    }

    public void sendMessage(Long chartId, String text) {
        sendMessage(chartId, text, null);
    }

    public void sendMessage(Long chartId, String text, List<String> buttons) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chartId.toString());
        reply.setText(text);
        if (buttons != null) {
            reply.setReplyMarkup(ButtonsCreator.createButton(buttons));
        }
        try {
           execute(reply);
        } catch (TelegramApiException e) {
           log.error("TelegramApiException " + e.getMessage());
        }
    }

    public void sendMessageWithRowButton(Long chartId, String text, List<String> buttons, int numberInRow, boolean remFirst) {
        SendMessage reply = new SendMessage();
        reply.setChatId(chartId.toString());
        reply.setText(text);
        if (buttons != null) {
            var markup = ButtonsCreator.createButtonRows(FileLoader.makeListOfList(buttons, numberInRow, remFirst));
            reply.setReplyMarkup(markup);
        }
        try {
            execute(reply);
        } catch (TelegramApiException e) {
           log.error("TelegramApiException " + e.getMessage());
        }
    }

    public void sendPhoto(Long chartId, String fileName, String caption) {
        try {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chartId.toString());
            sendPhoto.setPhoto(new InputFile(new FileInputStream(fileName), FileLoader.getSingleName(fileName)));
            sendPhoto.setCaption(caption);
            execute(sendPhoto);
        } catch (FileNotFoundException | TelegramApiException e) {
            log.error("Exception " + e.getMessage());
        }
    }

    public void sendPhoto(Long chartId, String fileName, String caption, List<String> buttons) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chartId.toString());
        try {
            sendPhoto.setPhoto(new InputFile(new FileInputStream(fileName), caption));
            if (!caption.isBlank()) sendPhoto.setCaption(caption);
            if (buttons != null) {
                sendPhoto.setReplyMarkup(ButtonsCreator.createButton(buttons));
            }
            execute(sendPhoto);
        } catch (IOException | TelegramApiException e) {
            log.error("TelegramApiException " + e.getMessage());
        }
    }

    public void sendDocument(Long chatId, String documentPath) {
            try {
                File document = new File(documentPath);
                if (!document.exists()) {
                    System.err.println("Document not found: " + documentPath);
                    return;
                }

                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId.toString());
                sendDocument.setDocument(new InputFile(document));

                execute(sendDocument);

            } catch (TelegramApiException e) {
                log.error("TelegramApiException. Error sending document " + e.getMessage());
            }
        }

    public void sendMedia(Long chartId, List<String> names) {
        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(chartId.toString());
        List<InputMedia> inputMediaList = new ArrayList<>();

        for (String fileName : names) {
            InputMediaPhoto inputMedia = new InputMediaPhoto();
            File file = new File(fileName);
            inputMedia.setMedia(file, fileName);
            inputMedia.setCaption(FileLoader.getSingleName(fileName));
            inputMediaList.add(inputMedia);
        }
        sendMediaGroup.setMedias(inputMediaList);
        try {
             execute(sendMediaGroup);
        } catch (TelegramApiException e) {
           log.error("TelegramApiException " + e.getMessage());
        }
    }

}
