
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.*;

import static org.mockito.Mockito.*;

class SewingBotTest {
    private static final Long CHAT_ID = 1L;
    private static final Message MESSAGE = new Message();
    private static final String BUTTON_DATA = "buttonData";
    private static final Chat CHAT = new Chat();
    private static final User USER = new User(1L,"Таня", false);
    private static final CallbackQuery CALLBACK_QUERY = new CallbackQuery();

    @BeforeEach
    void init(){
        CHAT.setId(CHAT_ID);
        MESSAGE.setChat(CHAT);
        MESSAGE.setFrom(USER);
        CALLBACK_QUERY.setMessage(MESSAGE);
        CALLBACK_QUERY.setData(BUTTON_DATA);
    }

    @Test
    void onUpdateReceivedTestWithoutMessageAndCallbackQuery (){
        SewingBot sewingBot = new SewingBot();
        Update update = mock(Update.class);
        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(false);

        sewingBot.onUpdateReceived(update);

        verify(update, Mockito.never()).getMessage();
        verify(update, Mockito.never()).getCallbackQuery();
    }

    @Test
    void onUpdateReceivedTestWithMessage(){
        SewingBot sewingBot = new SewingBot();
        Update update = mock(Update.class);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(MESSAGE);
        when(update.hasCallbackQuery()).thenReturn(false);

        sewingBot.onUpdateReceived(update);

        verify(update, atLeast(1)).getMessage();
        verify(update, never()).getCallbackQuery();
        sewingBot.sendMessage(CHAT_ID,anyString());
    }

    @Test
    void onUpdateReceivedTestWithCallbackQuery(){
        SewingBot sewingBot = new SewingBot();
        Update update = mock(Update.class);
        when(update.hasMessage()).thenReturn(false);
        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(CALLBACK_QUERY);

        sewingBot.onUpdateReceived(update);

        verify(update, never()).getMessage();
        verify(update, atLeast(1)).getCallbackQuery();
    }

}