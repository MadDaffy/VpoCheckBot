import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

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

    private SendMessage getSendMessage(Long chatId, String inputText) throws IOException {
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
