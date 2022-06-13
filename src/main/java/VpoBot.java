import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

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

    public static final String TEXT = "Федеральное агентство новостей (Riafan.ru) – современный интернет-ресурс, посвященный общественно-политической жизни в России и в мире. Оперативное и разностороннее освещение актуальных событий, интервью с политиками, эксклюзивные материалы из горячих точек: Riafan.ru предлагает своим читателям полноценную информационную картину дня."
            + "\n" + "https://riafan.ru/";
    public static final String START_ACTION = "Кликни" + "\n" + "/start";

    final int RECONNECT_PAUSE = 1000;

    private String botUsername;

    private String botToken;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        String chatId = update.getMessage().getChatId().toString();

        String inputText = update.getMessage().getText();
        log.info("{} input text is = {}", LOG_TAG, inputText);

        SendMessage message = getSendMessage(chatId, inputText);

        executeMessage(message);

    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        try {
            telegramBotsApi.registerBot(this);
            log.info("{} bot has been started", LOG_TAG);
        } catch (TelegramApiException e) {
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

    private SendMessage getSendMessage(String chatId, String inputText) throws IOException {
        SendMessage message;
        Parser parser = new Parser();
        if (inputText.startsWith("/start")) {
            message = new SendMessage(chatId, parser.getPage());
        } else {
            message = new SendMessage(chatId, START_ACTION);
        }

        return message;
    }
}
