package uk.co.mattbiggin.magorian.cli;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(subcommands = {
        Version.class,
        GenerateStats.class,
        Actions.class})
public class Main implements Callable<Void> {
    public static void main(String[] args) {
        CommandLine.call(new Main(), args);
    }

    @Override
    public Void call() {
        return null;
    }
}
