import gitbisect.GitBisect;

public class Main {

    public static void main(String[] args) {
        //SkillFactory.printNumbers();

        if (args.length != 3) {
            System.out.println("Wrong params! It should be <param1 param2 param3>");
        }

        System.out.println("Params are OK!\nLet's find first presence of bisect in Python repo!");

        String[] params = new String[3];
        System.arraycopy(args, 0, params, 0, args.length);

        new GitBisect().execute(params);
    }
}
