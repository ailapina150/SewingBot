import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TelegramSenderTest {

    @Test
    void testSendPhoto_fileNotFound()  {

        Long chatId = 123456789L;
        String fileName = "nonexistent_file.jpg"; //Имя несуществующего файла
        String caption = "Test Caption";
        TelegramSender sender = Mockito.spy(TelegramSender.class); //Класс, в котором находится sendPhoto


        sender.sendPhoto(chatId,fileName, caption);
        try {
            verify(sender, never()).execute(any(SendPhoto.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSendPhoto()  {
        Long chatId = 123456789L;
        String fileName = "./src/main/bag/model_1.jpg"; //Имя несуществующего файла
        String caption = "Test Caption";
        TelegramSender sender = Mockito.spy(TelegramSender.class); //Класс, в котором находится sendPhoto
        sender.sendPhoto(chatId,fileName, caption);
        try {
            verify(sender).execute(any(SendPhoto.class));
       } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}