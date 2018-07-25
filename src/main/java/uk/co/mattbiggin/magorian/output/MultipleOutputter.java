package uk.co.mattbiggin.magorian.output;

import com.google.common.collect.ImmutableList;
import uk.co.mattbiggin.magorian.model.GeneratedOutput;

import java.util.List;

public final class MultipleOutputter implements OutputWriter {
    private final List<OutputWriter> outputs;

    public MultipleOutputter(OutputWriter... writers) {
        outputs = ImmutableList.copyOf(writers);
    }

    @Override
    public void write(String type, GeneratedOutput output) {
        outputs.forEach(outputWriter -> outputWriter.write(type, output));
    }
}
