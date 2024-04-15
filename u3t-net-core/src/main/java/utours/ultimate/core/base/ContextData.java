package utours.ultimate.core.base;

import utours.ultimate.core.Context;

import java.io.BufferedReader;
import java.io.PrintWriter;

public record ContextData(
        PrintWriter writer,
        BufferedReader reader
) implements Context {
    
}
