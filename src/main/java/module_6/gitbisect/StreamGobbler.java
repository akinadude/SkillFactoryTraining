package module_6.gitbisect;

import java.io.InputStream;

public class StreamGobbler extends Thread {

    private final InputStream is;
    private final GobblerType type;
    private final ResultHandler handler;

    StreamGobbler(InputStream is, GobblerType type, ResultHandler handler) {
        this.is = is;
        this.type = type;
        this.handler = handler;
    }

    @Override
    public void run() {
        if (handler != null) {
            handler.handleResult(is, type);
        }
    }
}
