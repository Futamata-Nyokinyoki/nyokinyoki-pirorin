package com.nyokinyoki;

import org.jline.console.*;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.*;
import org.jline.utils.AttributedString;
import org.jline.widget.TailTipWidgets.TipType;
import org.jline.widget.TailTipWidgets;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        // Terminalオブジェクトの生成
        try (Terminal terminal = TerminalBuilder.terminal()) {
            Completer completer = new StringsCompleter("stamp", "syllabus");
            Parser parser = new DefaultParser();
            // LineReaderオブジェクトの生成
            LineReader reader = LineReaderBuilder.builder().terminal(terminal).completer(completer).parser(parser)
                    .build();
            Map<String, CmdDesc> tailTips = new HashMap<>();
            Map<String, List<AttributedString>> stampWidgetOpts = new HashMap<>();
            Map<String, List<AttributedString>> syllabusWidgetOpts = new HashMap<>();
            List<AttributedString> stampMainDesc = Arrays.asList(
                    new AttributedString("stamp"));
            List<AttributedString> syllabusMainDesc = Arrays.asList(
                    new AttributedString("syllabus"));

            stampWidgetOpts.put("-s", Arrays.asList(new AttributedString("打刻をする")));

            syllabusWidgetOpts.put("-d", Arrays.asList(new AttributedString("履修登録削除")));
            syllabusWidgetOpts.put("-s", Arrays.asList(new AttributedString("履修登録")));

            tailTips.put("stamp",
                    new CmdDesc(stampMainDesc, ArgDesc.doArgNames(Arrays.asList("-s")), stampWidgetOpts));
            tailTips.put("syllabus",
                    new CmdDesc(syllabusMainDesc, ArgDesc.doArgNames(Arrays.asList("-s=id", "-d=id")),
                            syllabusWidgetOpts));

            TailTipWidgets tailtipStampWidgets = new TailTipWidgets(reader, tailTips, 5, TipType.COMPLETER);
            tailtipStampWidgets.enable();

            while (true) {
                String line = reader.readLine("=====> ");
                System.out.println(line);
                if (line.equals("stamp")) {
                    // if(成功){
                    // 打刻;
                    // } else {
                    // **通常打刻：打刻しました**とメッセージを表示する
                    // }
                } else if (line.equals("syllabus")) {
                    // if(履修登録){
                    // 入力からidを取得して履修登録;
                    // if(時間割が被る){
                    // 最初に戻る;
                    // } else if (履修登録消去) {
                    // 入力からidを取得して履修消去;
                    // }
                    // if(成功){
                    // 戻る;
                    // }
                }
            }
            // その後の時間割表の表示;
        } catch (IOException | UserInterruptException ex) {
            System.out.println("Abrupt process");
        }
    }
}
