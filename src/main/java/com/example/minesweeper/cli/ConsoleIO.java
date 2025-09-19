package com.example.minesweeper.cli;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleIO {
    private final Scanner scanner;
    private final PrintStream out;

    public ConsoleIO(InputStream in, PrintStream out) {
        this.scanner = new Scanner(in);
        this.out = out;
    }

    public void println(String s) { out.println(s); }
    public void print(String s) { out.print(s); }
    public String readLine() { return scanner.nextLine(); }
}
