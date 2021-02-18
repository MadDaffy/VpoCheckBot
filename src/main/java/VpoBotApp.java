import org.telegram.telegrambots.ApiContextInitializer;

import java.util.logging.Logger;

public class VpoBotApp {

//    private static Logger logger = Logger.getLogger(VpoBotApp.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();

        VpoBot VPO_check_bot = new VpoBot ("VPO_check_bot", "1628820312:AAHq7FfWeoxp2hw8KUegsXziXg5B7YOG6gc");
        VPO_check_bot.botConnect();

    }

}
