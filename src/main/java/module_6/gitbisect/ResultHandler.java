package module_6.gitbisect;

import java.io.InputStream;

public interface ResultHandler {

    void handleResult(InputStream is, GobblerType type);
}
