package com.nyokinyoki.Terminal;

import java.util.Arrays;
import java.util.List;

import org.jline.utils.AttributedString;

public class CLIArg {
    private final String arg;
    private final List<AttributedString> desc;
    private final String pattern;

    public CLIArg(String desc, String arg, String pattern) {
        this.arg = arg;
        this.desc = Arrays.asList(new AttributedString(desc));
        this.pattern = pattern;
    }

    public String getArg() {
        return arg;
    }

    public List<AttributedString> getDesc() {
        return desc;
    }

    public String getPattern() {
        return pattern;
    }

}
