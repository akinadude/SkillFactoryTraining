package gitbisect;

import java.io.InputStream;

interface ResultHandler {

    void handleResult(InputStream is, GobblerType type);
}
