import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.mockito.Mockito.*;

class TelegramSenderTest {

    private static final Long CHAT_ID = 123456789L;
    private static final String CAPTION = "Test Caption";

    @Test
    void sendPhotoTest()  {

        String fileName = "./src/main/bag/model_1.jpg";
        TelegramSender sender = Mockito.spy(TelegramSender.class);

        sender.sendPhoto(CHAT_ID,fileName, CAPTION);

        try {
            verify(any(SendPhoto.class)).setChatId(anyString());
            verify(sender).execute(any(SendPhoto.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    void SendPhotoTestWhenFileNotFound()  {

        String fileName = "nonexistent_file.jpg"; //Имя несуществующего файла
        TelegramSender sender = Mockito.spy(TelegramSender.class);

        sender.sendPhoto(CHAT_ID,fileName, CAPTION);

        try {
            verify(sender, never()).execute(any(SendPhoto.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}