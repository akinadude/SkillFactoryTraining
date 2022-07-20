package gitbisect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GetCommitHashesHandler implements ResultHandler {

    private final List<String> commitHashes = new ArrayList<>();

    @Override
    public void handleResult(InputStream is, GobblerType type) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (type == GobblerType.Error) {
                    System.out.println(type + "> " + line);
                } else if (type == GobblerType.Output) {
                    commitHashes.add(line);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<String> getResult() {
        return commitHashes;
    }
}
