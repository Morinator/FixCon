package de.umr.fixcon.graphFunctions;

public abstract class AbstractIndicatorFunction extends AbstractGraphFunction {

    @Override
    public double optimum(int size, int... parameters) {
        return 1;
    }
}
