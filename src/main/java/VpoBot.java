import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VpoBot extends TelegramLongPollingBot {

    /**
     * Тег для логирования.
     */
    private static final String LOG_TAG = "[VpoBot] ::";

    public static final String TEXT = "Агентство \"Анадолу\" — крупнейшее турецкое информационное агентство, старейшее в Турции."
            + "\n" + "https://www.aa.com.tr/ru";
    public static final String START_ACTION = "Кликни" + "\n" + "/start";

    final int RECONNECT_PAUSE = 10000;

    private String botUsername;

    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {

        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();
        log.info("{} input text is = {}", LOG_TAG, inputText);

        SendMessage message = getSendMessage(chatId, inputText);

        executeMessage(message);
    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(this);
            log.info("{} bot has been started", LOG_TAG);
        } catch (TelegramApiRequestException e) {
            log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
        }

        reconnect();
    }

    private void reconnect() {
        try {
            Thread.sleep(RECONNECT_PAUSE);
        } catch (InterruptedException e) {
            botConnect();
        }
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage getSendMessage(Long chatId, String inputText) {
        SendMessage message;

        if (inputText.startsWith("/start")) {
            message = new SendMessage(chatId, TEXT);
        } else {
            message = new SendMessage(chatId, START_ACTION);
        }

        return message;
    }
}
