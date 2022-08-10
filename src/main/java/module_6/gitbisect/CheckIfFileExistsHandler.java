package module_6.gitbisect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CheckIfFileExistsHandler implements ResultHandler {

    private boolean fileExists = false;

    @Override
    public void handleResult(InputStream is, GobblerType type) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (type == GobblerType.Error) {
                    System.out.println(type + "> " + line);
                } else if (type == GobblerType.Output) {
                    if (line.contains("bisect.py")) {
                        fileExists = true;
                    }
                    if (line.contains("No such file")) {
                        fileExists = false;
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public boolean getResult() {
        return fileExists;
    }
}
