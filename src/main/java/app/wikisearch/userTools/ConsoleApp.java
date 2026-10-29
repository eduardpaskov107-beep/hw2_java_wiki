package app.wikisearch.userTools;

import app.wikisearch.service.WikiService;
import app.wikisearch.wikiexeption.WikiException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
  private Scanner scanner = new Scanner(System.in);
  private WikiService wikiService = new WikiService();

  public void start() {
    System.out.println(
        """
                Приветсвуем вас, сударь, в программе
                ВИКИПоиск
               """);
    while (true) {
      System.out.print("\nВведите запрос: ");
      String inputArticle = scanner.nextLine();
      if (inputArticle.trim().isEmpty()) {
        continue;
      }

      List<String> articles;
      try {
        articles = wikiService.search(inputArticle);
      } catch (WikiException e) {
        System.out.println(e.getMessage());
        continue;
      } catch (IOException e) {
        System.out.println("Сетевая ошибка: " + e.getMessage());
        continue;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println("Прервано");
        continue;
      }

      if (articles.isEmpty()) {
        System.out.println("Ничего не найдено(");
        continue;
      }

      int counter = 1;
      for (String article : articles) {
        System.out.println(counter + ": " + article);
        counter++;
      }
      System.out.print("\nВведите номер статьи (пустая строка чтобы выйти): ");
      String inputIdArticle = scanner.nextLine();

      if (inputIdArticle.trim().isEmpty()) {
        continue;
      }

      int articleId;
      try {
        articleId = Integer.parseInt(inputIdArticle);
      } catch (NumberFormatException e) {
        System.out.println("Неверный ввод(скорее всего введена не цифра)");
        continue;
      }

      if (articleId >= 1 && articleId <= articles.size()) {
        String articleTitle = articles.get(articleId - 1);
        openInBrowser(articleTitle);
      } else {
        System.out.println("Выход за пределы количества статей");
      }
    }
  }

  private void openInBrowser(String articleTitle) {
    try {
      String encodedTitle =
          URLEncoder.encode(articleTitle.replace(' ', '_'), StandardCharsets.UTF_8);
      String url = "https://ru.wikipedia.org/wiki/" + encodedTitle;
      Desktop.getDesktop().browse(new URI(url));
      System.out.println("Открывается: " + url);
    } catch (Exception e) {
      System.err.println("Не удалось открыть браузер: " + e.getMessage());
    }
  }
}
