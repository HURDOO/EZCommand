package kr.kro.ezcommand.engine.parser.file;

import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.ui.BlockList;
import org.json.simple.parser.ParseException;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileLoader {
    public static void loadEZPacks() {
        List<File> list = temp_loadEZPacks();

        for(File file : list) {
            try {
                FileParser.parse(file);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<File> getFiles(File dir) {
        List<File> list = new ArrayList<>();

        for(File file : dir.listFiles()) {
            //System.out.println(file.getName());
            if(file.isDirectory())
                list.addAll(getFiles(file));
            else
                list.add(file);
        }
        return list;
    }


    private static List<File> temp_loadEZPacks() {
        URL root = FileLoader.class.getResource("/MC_Java_1.16.5");

        List<File> list = new ArrayList<>();
        try {
            list.addAll(getFiles(new File(root.toURI())));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return list;
    }
}
