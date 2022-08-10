package module_6.gitbisect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LogHandler implements ResultHandler {

    @Override
    public void handleResult(InputStream is, GobblerType type) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(type + "> " + line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
