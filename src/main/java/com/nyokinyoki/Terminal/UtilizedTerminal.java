package com.nyokinyoki.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jline.builtins.Completers.RegexCompleter;
import org.jline.console.ArgDesc;
import org.jline.console.CmdDesc;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Parser;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.widget.TailTipWidgets;
import org.jline.widget.TailTipWidgets.TipType;

public class UtilizedTerminal {
    private static final String syllabus = "syllabus";
    private static final String stamp = "stamp";

    private static Map<String, CmdDesc> tailTips = new HashMap<>();

    private static List<CLIArg> syllabusOptions = Options.getSyllabusOptions();
    private static List<CLIArg> stampOptions = Options.getStampOptions();

    private LineReader reader;
    private Completer completer;
    private Parser parser;

    public UtilizedTerminal() {
        this.completer = createCompleter();
        this.parser = new DefaultParser();
        this.reader = initializeTerminal();
        showMessages();
    };

    public static List<CLIArg> getAllOptions() {
        List<CLIArg> newList = Stream.concat(syllabusOptions.stream(), stampOptions.stream()).toList();
        return newList;
    }

    public LineReader getReader() {
        return reader;
    }

    private LineReader initializeTerminal() {
        try (Terminal terminal = TerminalBuilder.terminal()) {
            LineReader reader = LineReaderBuilder.builder().terminal(terminal).completer(completer).parser(parser)
                    .build();
            TailTipWidgets tailtipStampWidgets = new TailTipWidgets(reader, tailTips, 5, TipType.COMPLETER);
            tailtipStampWidgets.enable();

            return reader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Completer createCompleter() {
        List<AttributedString> stampMainDesc = Arrays.asList(new AttributedString(stamp));
        List<AttributedString> syllabusMainDesc = Arrays.asList(new AttributedString(syllabus));

        Map<String, List<AttributedString>> stampWidgetOpts = new HashMap<>();
        Map<String, List<AttributedString>> syllabusWidgetOpts = new HashMap<>();

        stampOptions.forEach(option -> stampWidgetOpts.put(option.getArg(), option.getDesc()));
        syllabusOptions.forEach(option -> syllabusWidgetOpts.put(option.getArg(), option.getDesc()));

        List<String> stampDescList = stampOptions.stream().map(option -> option.getArg()).collect(Collectors.toList());
        List<String> syllabusDescList = syllabusOptions.stream().map(option -> option.getArg())
                .collect(Collectors.toList());

        Map<String, Completer> comp = setCompleter(stampDescList, syllabusDescList);
        tailTips.put(stamp, new CmdDesc(stampMainDesc, ArgDesc.doArgNames(stampDescList), stampWidgetOpts));
        tailTips.put(syllabus, new CmdDesc(syllabusMainDesc, ArgDesc.doArgNames(syllabusDescList), syllabusWidgetOpts));
        return new RegexCompleter("C1 C11? | C2 C21?", comp::get);
    }

    private static void showMessages() {
        System.out.println("打刻関連: stamp");
        System.out.println("履修関連: syllabus");
        System.out.println("Please press TAB key to continue");
    }

    private static Map<String, Completer> setCompleter(List<String> stampDescList, List<String> syllabusDescList) {
        Map<String, Completer> completer = new HashMap<>();
        completer.put("C1", new StringsCompleter("stamp"));
        completer.put("C11", new StringsCompleter(stampDescList.toArray(new String[0])));
        completer.put("C2", new StringsCompleter("syllabus"));
        completer.put("C21", new StringsCompleter(syllabusDescList.toArray(new String[0])));
        return completer;
    }

    public static void helpMessage() {
        System.out.println("実行可能コマンド");

        for (CLIArg cmd : syllabusOptions) {
            System.out.printf("syllabus %-50s %s\n", cmd.getArg(), cmd.getDesc());
        }
        for (CLIArg cmd : stampOptions) {
            System.out.printf("stamp    %-50s %s\n", cmd.getArg(), cmd.getDesc());
        }
    }
}
