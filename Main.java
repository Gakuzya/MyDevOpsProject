import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        // Создаем сервер, который будет слушать порт 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Говорим, что отвечать, когда кто-то заходит на главную страницу ("/")
        server.createContext("/", exchange -> {
            String response = "Privet! Eto moy perviy Java web-server v Docker!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });
        
        // Запускаем сервер
        server.start();
        System.out.println("Сервер запущен и работает на порту 8080...");
    }
}