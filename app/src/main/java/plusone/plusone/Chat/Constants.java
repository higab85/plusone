package plusone.plusone.Chat;

import plusone.plusone.ServerConnection;

public class Constants {
//    public static final String CHAT_SERVER_URL = "https://socket-io-chat.now.sh/";
//    public static final String CHAT_SERVER_URL = "http://10.60.59.89:5000";
//    public static final String CHAT_SERVER_URL = "http://192.168.1.38:5000";
    public static final String CHAT_SERVER_URL = ServerConnection.INSTANCE.getUrlHost();
}
