package kr.kro.ezcommand.engine.parser.file;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kr.kro.ezcommand.EZCommand;
import kr.kro.ezcommand.engine.parser.ezc.EZBlockParser;
import kr.kro.ezcommand.engine.parser.ezc.YAMLParser;
import kr.kro.ezcommand.engine.thirdparty.EZPack;
import kr.kro.ezcommand.engine.parser.EZBlock;
import kr.kro.ezcommand.ui.BlockList;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FileParser
{
    private static Gson gson = new Gson();

    public static void parse(File file) throws IOException{

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
            EZPack pack = YAMLParser.parseEZPack(map);
            EZCommand.packs.add(pack);
            return;
        }

        JsonObject object = gson.fromJson(new FileReader(file,StandardCharsets.UTF_8),JsonObject.class);

        String type = object.get("type").getAsString();
        switch(type)
        {
            case "ezblock": // EZBlock
                EZBlock block = EZBlockParser.parse(object);
                BlockList.addExampleBlock(block);
                break;
            /*case "nbt":
                NBT nbt = NBTParser.parse(object);
                break;*/
            case "entity":
                System.out.println(file.getName());
                return;
            default: // nothing
                if(!EZCommand.datas.containsKey(type)) throw new IllegalArgumentException("Illegal type!");
                Class<? extends EZData> dataClass = EZCommand.datas.get(type);
                EZDataParser parser = EZCommand.dataParsers.get(type);

                EZData data = parser.parse(object);
                if(data.getClass() != dataClass)
                    throw new UnsupportedOperationException(data.getClass().getName() + " is not " + dataClass.getName());
        }

    }
}
