package jurl;

/**
 * Driver class - Jurl program starts here
 *
 * @author ZK
 */
public class Jurl {

    /**
     * start program
     *
     * @param args the commands to be done
     */
    public static void main(String[] args) {
        CommandListener commandListener = new CommandListener(args);
    }
}