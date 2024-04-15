package utours.ultimate.core;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface Context {
    
    PrintWriter writer();

    BufferedReader reader();

}
