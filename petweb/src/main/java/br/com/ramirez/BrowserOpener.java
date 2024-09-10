package br.com.ramirez;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import jakarta.enterprise.event.Observes;


@QuarkusMain
public class BrowserOpener {

    public void onStart(@Observes StartupEvent ev) {
        // URL que queremos abrir no navegador ao iniciar a aplicação
        String url = "http://localhost:8080/start";

        // Abre o navegador automaticamente
        openBrowser(url);
    }

    public static void openBrowser(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop browsing is not supported on this platform.");
        }
    }
    public static void main(String[] args) {
        // Inicia a aplicação Quarkus
        Quarkus.run(args);

        // URL que queremos abrir no navegador ao iniciar a aplicação
        String url = "http://localhost:8080/start";

        // Abre o navegador automaticamente
        openBrowser(url);
    }
}

