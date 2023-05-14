package com.nyokinyoki.Terminal;

import java.util.ArrayList;
import java.util.List;

public class Options {

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
        CLIArg removeCourseCLI = new CLIArg("<LECTURE_ID>がIDの講義を履修解除", removeCourseArg,
                "\s*syllabus\s+--remove-course@([0-9]+)\s*");
        CLIArg stampCLI = new CLIArg("<yyyy-MM-dd-HH:mm:ss>の時刻に打刻をする", stampArg,
                "\s*stamp\s+--time@(\\d{4}-\\d{2}-\\d{2}-\\d{2}:\\d{2}:\\d{2})\s*");
        CLIArg showAllStampHistoryCLI = new CLIArg("すべての打刻を表示", showAllStampHistoryArg,
                "\s*stamp\s+--show-all-stamp-history\s*");
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

    public static List<CLIArg> getSyllabusOptions() {
        return syllabusOptions;
    }

    public static List<CLIArg> getStampOptions() {
        return stampOptions;
    }
}
