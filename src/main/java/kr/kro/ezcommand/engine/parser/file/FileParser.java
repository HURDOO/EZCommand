package kr.kro.ezcommand.engine.parser.file;

import kr.kro.ezcommand.EZProject;
import kr.kro.ezcommand.engine.javaloader.EZPack;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.engine.parser.NBT;
import kr.kro.ezcommand.engine.parser.type.*;
import kr.kro.ezcommand.engine.parser.type.Number;
import kr.kro.ezcommand.engine.parser.type.rjtf.RawJsonTextFormat;
import kr.kro.ezcommand.ui.BlockList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FileParser
{
    private static JSONParser parser = new JSONParser();

    public static void parse(File file) throws IOException, ParseException {

        // YAML
        if(file.getName().equals("ezpack.yml")) {
            Yaml yaml = new Yaml();

            Map<String,Object> map;
            try {
                map = yaml.load(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
            EZPack pack = YAMLParser.parse(map);
            EZProject.getPacks().add(pack);
            return;
        }

        JSONObject object = (JSONObject) parser.parse(new FileReader(file,StandardCharsets.UTF_8));
        String type = object.get("type").toString();
        switch(type)
        {
            case "ezblock": // EZBlock
                EZBlock block = EZBlockParser.parse(object);
                BlockList.addExampleBlock(block);
                break;
            case "nbt":
                NBT nbt = NBTParser.parse(object);
                break;
            case "entity":
                System.out.println(file.getName());
                return;
            default: // nothing
                throw new IllegalArgumentException("Illegal type!");
        }

    }
}
