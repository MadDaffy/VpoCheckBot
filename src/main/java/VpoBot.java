import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.logging.Logger;

@AllArgsConstructor
@NoArgsConstructor
public class VpoBot extends TelegramLongPollingBot {

//   private final static Logger logger = Logger.getLogger(VpoBot.class);

   final int RECONNECT_PAUSE = 10000;

   @Getter
   @Setter
   String userName;
   @Getter
   @Setter
   String token;

    @Override
    public void onUpdateReceived(Update update) {

//         log.debug("Receive new Update. updateID: " + update.getUpdateId());
        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();
        System.out.println(inputText);

        if(inputText.startsWith("/start")){
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Агентство \"Анадолу\" — крупнейшее турецкое информационное агентство, старейшее в Турции."+"\n"+"https://www.aa.com.tr/ru");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Кликни"+"\n"+"/start");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    public String getBotUsername() {
        return userName;
    }

    public String getBotToken() {
        return token;
    }

    public void botConnect(){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(this);
            System.out.println("Telegram Bot started");
//            logger.info("Telegram Bot started");
        } catch (TelegramApiRequestException e) {
            System.out.println("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
//            logger.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
        }


        try {
            Thread.sleep(RECONNECT_PAUSE);
        } catch (InterruptedException e) {
            botConnect();
        }


    }
}
