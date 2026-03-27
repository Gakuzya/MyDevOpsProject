import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestApp {
    public static void main(String[] args) throws Exception {
        System.out.println("⏳ Запускаем автоматический тест...");
        
        // Создаем виртуального "пользователя" (браузер)
        HttpClient client = HttpClient.newHttpClient();
        
        // Формируем запрос на наш локальный сайт (порт 80, где сидит Nginx)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost/"))
                .GET()
                .build();
        
        // Отправляем запрос и получаем ответ
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        // То, что мы ОЖИДАЕМ увидеть
        String expectedText = "Privet! Eto moy perviy Java web-server v Docker!";
        
        // Сравниваем реальность с ожиданиями
        if (response.statusCode() == 200 && response.body().equals(expectedText)) {
            System.out.println("✅ ТЕСТ ПРОЙДЕН: Сервер работает и выдает правильный текст!");
        } else {
            System.err.println("❌ ОШИБКА: Сервер сломан или выдал неправильный результат!");
            System.err.println("Ожидалось: " + expectedText);
            System.err.println("Получено: " + response.body());
            System.exit(1); // Код 1 говорит системе (и будущему DevOps), что всё плохо
        }
    }
}