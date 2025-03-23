
import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Log4j
public class Start {
    public static void main(String[] args) {
        try {
            log.info("---------APPLICATION STARTED------------");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

            DefaultBotOptions botOptions = new DefaultBotOptions();
            botOptions.setProxyHost(AppProperties.PROXY_HOST);
            botOptions.setProxyPort(AppProperties.PROXY_PORT);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS4);

            SewingBot myBot = new SewingBot();
            telegramBotsApi.registerBot(myBot);
            log.info("Sewing bot registered");
        } catch (TelegramApiException e) {
            log.error("TelegramApiException: " + e.getMessage());
        }
    }
}
