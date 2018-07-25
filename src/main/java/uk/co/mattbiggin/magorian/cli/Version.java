package uk.co.mattbiggin.magorian.cli;

import picocli.CommandLine;
import uk.co.mattbiggin.magorian.version.ApplicationProperties;
import uk.co.mattbiggin.magorian.version.ResourceApplicationProperties;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;

@Command(description = "Show version number", name = "version", mixinStandardHelpOptions = true)
public class Version implements Callable<Void> {
    public static void main(String[] args) {
        // CheckSum implements Callable, so parsing, error handling and handling user
        // requests for usage help or version help can be done with one line of code.
        CommandLine.call(new Version(), args);
    }

    @Override
    public Void call() {
        ApplicationProperties properties = new ResourceApplicationProperties();
        System.out.println(properties.getName() + " v" + properties.getVersion());
        return null;
    }
}
