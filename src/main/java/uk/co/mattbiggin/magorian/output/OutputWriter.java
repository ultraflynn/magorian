package uk.co.mattbiggin.magorian.output;

import uk.co.mattbiggin.magorian.model.GeneratedOutput;

public interface OutputWriter {
    void write(String type, GeneratedOutput output);
}
