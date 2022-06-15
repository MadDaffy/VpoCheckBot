import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@Slf4j
@Getter
@Setter
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

    private ArrayList<URL> urlNewsList = getAllUrlList();
    private URL url;
    {
        try {
            url = new URL("https://www.aa.com.tr/ru/rss/default?cat=live");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private String botUsername;

    private String botToken;

    public VpoBot(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        String chatId = update.getMessage().getChatId().toString();

        String inputText = update.getMessage().getText();
        log.info("{} input text is = {}", LOG_TAG, inputText);
        ArrayList<SendMessage> listNews = getSendMessage(chatId, inputText);
        for (SendMessage sendMessage : listNews) {
            sendMessage.disableWebPagePreview();
            executeMessage(sendMessage);
        }
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

    private ArrayList<SendMessage> getSendMessage(String chatId, String inputText) throws IOException {

        Parser parser = new Parser();
        String stringNews = parser.getPage(url);
        ArrayList<SendMessage> listMessage = new ArrayList<>();
        if (inputText.startsWith("/start")) {
            int stepMaxChars = 4000;
            int startIndex = 0;
            while (startIndex + stepMaxChars < stringNews.length()) {
                String partOfNews = stringNews.substring(startIndex, startIndex+stepMaxChars);
                listMessage.add(new SendMessage(chatId, partOfNews));
                startIndex += stepMaxChars;
            }
            //The last part of News
            listMessage.add(new SendMessage(chatId, stringNews.substring(startIndex)));

        } else {
            listMessage.add(new SendMessage(chatId, START_ACTION));
        }
        return listMessage;
    }

    private ArrayList<URL> getAllUrlList() {
        ArrayList<URL> listNewsUrl = new ArrayList<>();
        try {
            listNewsUrl.add(new URL("https://www.aa.com.tr/ru/rss/default?cat=live"));
            listNewsUrl.add(new URL("https://sana.sy/ru/?feed=rss2"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return listNewsUrl;
    }
}
