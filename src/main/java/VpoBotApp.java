import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VpoBotApp {

    /**
     * Тег для логирования.
     */
    private static final String LOG_TAG = "[VpoBotApp ] ::";

    public static void main(String[] args) {
        log.info("{} trying to init api context", LOG_TAG);

//        ApiContextInitializer.init();
        log.info("{} api context has been initialized", LOG_TAG);

        VpoBot VPO_check_bot = new VpoBot("VPO_check_bot", "1628820312:AAHq7FfWeoxp2hw8KUegsXziXg5B7YOG6gc");

        VPO_check_bot.botConnect();
    }

}
