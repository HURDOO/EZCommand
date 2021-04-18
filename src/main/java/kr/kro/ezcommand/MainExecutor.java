package kr.kro.ezcommand;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MainExecutor {
    public static void main(String[] args) {
        //Main.main(args);

        StringBuilder builder = new StringBuilder();
        try {
            FileReader reader = new FileReader(Objects.requireNonNull(MainExecutor.class.getResource("/files/test3.json")).getFile(), StandardCharsets.UTF_8);
            /*Scanner src = new Scanner(reader);
            while(src.hasNext())
                builder.append(src.next());
            System.out.println(builder.);*/
            JSONArray array = (JSONArray) new JSONParser().parse(reader);
            boolean skip = false;
            for(int i=0;i< array.size();i++)
            {
                if(skip)
                {
                    skip = false;
                    continue;
                }
                String str = array.get(i).toString();
                if(i%2==0) // key
                {
                    if(str.contains("banner") && !str.contains("banner\""))
                    {
                        skip = true;
                        continue;
                    }
                    System.out.println(str);
                }
                else // value
                {
                    System.out.println(str + "\n");
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
