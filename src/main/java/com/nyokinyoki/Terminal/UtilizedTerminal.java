package com.nyokinyoki.Terminal;

import org.jline.builtins.Completers.RegexCompleter;
import org.jline.console.*;
import org.jline.reader.*;
import org.jline.reader.Completer;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.*;
import org.jline.utils.AttributedString;
import org.jline.widget.TailTipWidgets.TipType;

import org.jline.widget.TailTipWidgets;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtilizedTerminal {
    private static final String syllabus = "syllabus";
    private static final String stamp = "stamp";

    private static Map<String, CmdDesc> tailTips = new HashMap<>();

    private static List<CLIArg> syllabusOptions = new ArrayList<>();
    private static List<CLIArg> stampOptions = new ArrayList<>();

    static {
        String showRegisteredCoursesArg = "--show-registered-courses";
        String getAvailableCoursesArg = "--get-available-courses";
        String getAvailableCoursesByTimeSlotArg = "--get-available-courses-by-time-slot--day-of-week@<DAY_OF_WEEK>--beginning-period@<BEGINNIG_PERIOD>";
        String addCourseArg = "--add-course@<LECTURE_ID>";
        String removeCourseArg = "--remove-course@<LECTURE_ID>";
        String stampArg = "--time@<yyyy-MM-dd-HH:mm:ss>";
        String showAllStampHistoryArg = "--show-all-stamp-history";
        String showAttendHistoryByDateArg = "--show-attend-history-by-date@<yyyy-MM-dd>";
        String showAttendHistoryByCourseArg = "--show-attend-history-by-course@<COURSE_ID>";

        CLIArg showRegisteredCoursesCLI = new CLIArg("登録済みの授業の表示", showRegisteredCoursesArg,
                "\s*syllabus\s+--show-registered-courses\s*");
        CLIArg getAvailableCoursesCLI = new CLIArg("登録可能な授業の取得", getAvailableCoursesArg,
                "\s*syllabus\s+--get-available-courses\s*");
        CLIArg getAvaliableCoursesByTimeSlotCLI = new CLIArg("<DAY_OF_WEEK>曜日(月曜：1, 金曜：5)の<BEGINNING_PERIOD>コマにある授業を表示",
                getAvailableCoursesByTimeSlotArg,
                "\s*syllabus\s+--get-available-courses-by-time-slot--day-of-week@([1-5])--beginning-period@([1-5])\s*");
        CLIArg addCourseCLI = new CLIArg("<LECTURE_ID>がIDの講義を履修登録", addCourseArg,
                "\s*syllabus\s+--add-course@([0-9]+)\s*");
        CLIArg removeCourseCLI = new CLIArg("<LECTURE_ID>がIDを履修解除", removeCourseArg,
                "\s*syllabus\s+--remove-course@([0-9]+)\s*");
        CLIArg stampCLI = new CLIArg("<yyyy-MM-dd-HH:mm:ss>の時刻に打刻をする", stampArg,
                "\s*stamp\s+--time@(\\d{4}-\\d{2}-\\d{2}-\\d{2}:\\d{2}:\\d{2})\s*");
        CLIArg showAllStampHistoryCLI = new CLIArg("すべての打刻を表示", showAllStampHistoryArg,
                "\s*stamp\s+--show-all-stamp-history-arg\s*");
        CLIArg showAttendHistoryByDateCLI = new CLIArg("<yyyy-MM-dd>のすべての打刻を表示", showAttendHistoryByDateArg,
                "\s*stamp\s+--show-attend-history-by-date@([0-9]{4}-[0-9]{2}-[0-9]{2})\s*");
        CLIArg showAttendHistoryByCourseCLI = new CLIArg("<COURSE_ID>の講義のすべての打刻を表示", showAttendHistoryByCourseArg,
                "\s*stamp\s+--show-attend-history-by-course@([0-9]+)");

        syllabusOptions.add(showRegisteredCoursesCLI);
        syllabusOptions.add(getAvailableCoursesCLI);
        syllabusOptions.add(getAvaliableCoursesByTimeSlotCLI);
        syllabusOptions.add(addCourseCLI);
        syllabusOptions.add(removeCourseCLI);

        stampOptions.add(stampCLI);
        stampOptions.add(showAllStampHistoryCLI);
        stampOptions.add(showAttendHistoryByDateCLI);
        stampOptions.add(showAttendHistoryByCourseCLI);
    }

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

        Map<String, Completer> comp = new HashMap<>();
        comp.put("C1", new StringsCompleter("stamp"));
        comp.put("C11", new StringsCompleter(stampDescList.toArray(new String[0])));
        comp.put("C2", new StringsCompleter("syllabus"));
        comp.put("C21", new StringsCompleter(syllabusDescList.toArray(new String[0])));

        tailTips.put(stamp, new CmdDesc(stampMainDesc, ArgDesc.doArgNames(stampDescList), stampWidgetOpts));
        tailTips.put(syllabus, new CmdDesc(syllabusMainDesc, ArgDesc.doArgNames(syllabusDescList), syllabusWidgetOpts));
        return new RegexCompleter("C1 C11? | C2 C21?", comp::get);
    }

    private static void showMessages() {
        System.out.println("打刻関連: stamp");
        System.out.println("履修関連: syllabus");
        System.out.println("Please press TAB key to continue");
    }
}
