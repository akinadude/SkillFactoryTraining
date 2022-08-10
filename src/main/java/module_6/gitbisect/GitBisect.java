package module_6.gitbisect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitBisect {

    List<String> commitHashes = new ArrayList<>();

    /*
    * 1. Go to cpython directory
    * 2. Perform java -classpath <path-to-classes> Main "7f777ed95a19224294949e1b4ce56bbffcb1fe9f" "dd104400dc551dd4098f35e12072e12c45822adc" "test -f 'Lib/bisect.py'"
    * */

    public void execute(String[] params) {
        String firstCommitHash = params[0];
        String lastCommitHash = params[1];
        String command = params[2];

        commitHashes = getCommitHashes(firstCommitHash, lastCommitHash);
        System.out.println("Commit hashes number: " + commitHashes.size());

        int attemptsCounter = 0;
        int left = 0;
        int right = commitHashes.size() - 1;
        while (left != right - 1) {
            attemptsCounter++;
            System.out.println("Attempt #" + attemptsCounter);

            int mid = (left + right) / 2;
            System.out.println("Commit hash" + commitHashes.get(mid));

            //todo The most right commit hash for which check result is true
            boolean checkResult = checkCommitHash(commitHashes.get(mid), command);

            if (checkResult) {
                left = mid;
            } else {
                right = mid;
            }
        }

        System.out.println("The needed commit hash is: " + commitHashes.get(left));

        //9 7 5 |5 5 4| 3 1
    }

    private List<String> getCommitHashes(String firstCommitHash, String lastCommitHash) {
        List<String> commands = new ArrayList<>();
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add("git rev-list --ancestry-path " + firstCommitHash + ".." + lastCommitHash);

        GetCommitHashesHandler handler = new GetCommitHashesHandler();

        invokeBashCommands(commands, handler);

        return handler.getResult();
    }

    private boolean checkCommitHash(String commitHash, String command) {
        checkoutByCommitHash(commitHash);
        return checkIfFileExists(command);
    }

    private void checkoutByCommitHash(String commitHash) {
        List<String> commands = new ArrayList<>();
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add("git checkout --force " + commitHash);

        invokeBashCommands(commands, new LogHandler());
    }

    private boolean checkIfFileExists(String command) {
        List<String> commands = new ArrayList<>();
        commands.add("/bin/bash");
        commands.add("-c");
        commands.add(command);

        CheckIfFileExistsHandler handler = new CheckIfFileExistsHandler();

        int result = invokeBashCommands(commands, handler);

        return result == 0;
    }

    private int invokeBashCommands(List<String> commands, ResultHandler handler) {

        int exitCode = -1;

        Process process;
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.redirectErrorStream(true);

        try {
            process = processBuilder.start();

            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), GobblerType.Error, handler);
            StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), GobblerType.Output, handler);
            outputGobbler.start();
            errorGobbler.start();

            int exit = process.waitFor();
            errorGobbler.join();
            outputGobbler.join();

            //System.out.println("Exit code: " + exit);

            /*if (exit != 0) {
                throw new AssertionError(String.format("CLI command returned %d", exit));
            }*/

            exitCode = exit;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return exitCode;
    }
}
