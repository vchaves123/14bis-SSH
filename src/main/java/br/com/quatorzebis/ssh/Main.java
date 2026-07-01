package br.com.quatorzebis.ssh;

import br.com.quatorzebis.ssh.ui.MainWindow;
import org.eclipse.swt.widgets.Display;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        redirectConsoleToLog();
        Display display = new Display();
        try {
            new MainWindow(display).open();
        } finally {
            if (!display.isDisposed()) display.dispose();
        }
    }

    private static void redirectConsoleToLog() {
        try {
            Path logDir = Path.of(System.getProperty("user.home"), ".14bis", "log");
            Files.createDirectories(logDir);
            String ts   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path   file = logDir.resolve(ts + "_app.log");
            PrintStream ps = new PrintStream(
                Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.APPEND),
                true, "UTF-8");
            System.setOut(ps);
            System.setErr(ps);
        } catch (IOException e) {
            // If we can't open the log, keep the original console — not fatal.
        }
    }
}
