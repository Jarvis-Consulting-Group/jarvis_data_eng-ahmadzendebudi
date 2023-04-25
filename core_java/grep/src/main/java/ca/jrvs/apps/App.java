package ca.jrvs.apps;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.jrvs.apps.grep.JavaGrep;
import ca.jrvs.apps.grep.JavaGrepImpl;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        String regex = args[0];
        String rootPath = args[1];
        String outFile = args[2];
        JavaGrep javaGrep = new JavaGrepImpl(regex, rootPath, outFile);

        try {
            javaGrep.process();
        } catch (IOException e) {
            logger.error("Error: Unable to process", e);
        }
    }
}
