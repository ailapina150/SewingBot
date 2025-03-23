import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
public class SewingBot extends TelegramSender{
    private final Map<Long, BotUser> botUsers;

    public SewingBot() {
        botUsers = new HashMap<>();

        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand(ActButton.START.getCommand(), ActButton.START.getName()));
        listOfCommands.add(new BotCommand(ActButton.ORDER.getCommand(), ActButton.ORDER.getName()));
        listOfCommands.add(new BotCommand(ActButton.DONAT.getCommand(), ActButton.DONAT.getName()));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasCallbackQuery() && !update.hasMessage()) return;
        long chatId = getChatId(update);
        Message message = getMessage(update);
        String text = message.hasText() ? message.getText() : "";
        log.debug("text = " + text);
        String buttonData = getButtonData(update);
        log.debug("buttonData = " + buttonData);

        usersIdCollector(chatId, message);
        BotUser botUser = botUsers.get(chatId);

        if (text.equals(ActButton.START.getCommand())) {
            sendHiMessage(chatId, message);
        }else if (text.equals(ActButton.DONAT.getCommand())){
            sendInformationForDonat(chatId);
        } else if(buttonData.equals(ActButton.INTERESTING.getName()) || (text.equals(ActButton.ORDER.getCommand()))){
            showProduct(chatId);
        } else if(buttonData.equals(ActButton.NOT_INTERESTING.getName())){
            sendMessage(chatId,"Хорошего дня!");
        } else if(selectProduct(chatId, buttonData, botUser)){
            sendMessageWithRowButton(chatId, "Хотите получить лекала для изготовления изделия?", ActButton.getSendButton(),2, false);
        } else if(buttonData.equals(ActButton.YES.getName())){
            sendDocument(chatId,AppProperties.SKETCH + "/"+ botUser.getModelName() +".pdf");
            sendMessage(AppProperties.COLLECTOR_CHAT_ID, "@" + botUser.getUserName() +
                    " Id: " + chatId + " " + botUser.getFirstName() + " скачал "+ botUser.getModelName());
            sendInformationForDonat(chatId);
        }else if(buttonData.equals(ActButton.NO.getName())){
            sendMessage(chatId,"Прокрутите вверх и выберите другое изделие");
        }
    }

    private void usersIdCollector(Long chatId, Message message) {
        BotUser botUser = botUsers.get(chatId);
        User user = message.getFrom();

        if (botUser == null) {
            if (user.getId() != AppProperties.BOT_USER_ID) {
                botUser = new BotUser(user.getId(), user.getFirstName());
                botUser.setUserName(user.getUserName());
            } else {
                botUser = new BotUser(chatId);
            }
            botUsers.put(chatId, botUser);
            sendMessage(AppProperties.COLLECTOR_CHAT_ID, "@" + botUser.getUserName() +
                    " Id: " + chatId + " " + botUser.getFirstName() );
        } else if (chatId != AppProperties.BOT_USER_ID && user.getFirstName().equals("")) {
            botUser.setUserName(user.getUserName());
            botUser.setFirstName(user.getFirstName());
            botUser.setUserName(user.getUserName());
            sendMessage(AppProperties.COLLECTOR_CHAT_ID, "@" + botUser.getUserName() +
                    " Id: " + chatId + " " + botUser.getFirstName());
        }
    }


    private void sendHiMessage(long chatId, Message message) {
        String firstMessage =
                ", здравствуйте 👋. Мы занимаемся изготовлением эксклюзивных швейных изделий \uD83E\uDDF5 \uD83E\uDEA1 " +
                        "и предлагаем Вам ознакомится с нашей работой. " +
                        "Здесь вы так же сможите скачать бесплатные лекала для сытья. " +
                        "Вам интресно?";
        sendMessageWithRowButton(chatId,
                message.getChat().getFirstName() + firstMessage,
                ActButton.getStartButton(),
                2, false);
    }

    private void showProduct(long chatId){
        for (List<String> names : ProductLoader.get()) {
            if (names.size() > 1) {
                sendMedia(chatId, names);
                sendMessageWithRowButton(chatId, ActButton.ORDER.getReplay(),
                    FileLoader.getAllSingleNames(names),
                    AppProperties.NUMBER_BUTTON_IN_ROW,
                    true);
                } else {
                     sendPhoto(chatId, names.get(0),
                        ActButton.ORDER.getReplay(),
                        FileLoader.getAllSingleNames(names));
            }
        }
    }

    private boolean selectProduct(Long chatId,String buttonData, BotUser botUser){
        for (List<String> names : ProductLoader.get()){
            for(String name : names) {
                if( FileLoader.getSingleName(name).equals(buttonData)){
                    sendPhoto(chatId, name, FileLoader.getSingleName(name));
                    log.debug("name = " + name);
                    botUser.setModelName(FileLoader.getSingleName(name));
                    return true;
                }
            }
        }
        return false;
    }

    private void sendInformationForDonat(Long chatId){
        sendMessage(chatId, """
                Поддержать проект:
                Если вам понравились наши лекала, вы можете сделать пожертвование на развитие проекта.
                Номер карты 1111-1111-1111-
                
                Благодарим за содействие!
                """);
    }

}
