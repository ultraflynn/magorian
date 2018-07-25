package uk.co.mattbiggin.magorian.output;

import uk.co.mattbiggin.magorian.model.GeneratedOutput;

public final class ConsoleOutputWriter implements OutputWriter {
    @Override
    public void write(String type, GeneratedOutput generatedOutput) {
        generatedOutput.getOutput().forEach(System.out::println);
    }
}
