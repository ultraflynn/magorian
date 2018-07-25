package uk.co.mattbiggin.magorian.cli;

import picocli.CommandLine;

import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;

@Command(description = "Show all APM actions", name = "actions", mixinStandardHelpOptions = true)
public final class Actions implements Callable<Void> {
    public static void main(String[] args) {
        // CheckSum implements Callable, so parsing, error handling and handling user
        // requests for usage help or version help can be done with one line of code.
        CommandLine.call(new Actions(), args);
    }

    @Override
    public Void call() {
        System.out.println("Review absences");
        System.out.println("Weekly statistics");
        System.out.println("Inactivity report");
        System.out.println("PO applicant review");
        System.out.println("QQs and Interviews (2x QQ, 2x Interview)");
        return null;
    }
}
