package kr.kro.ezcommand;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MainExecutor {
    public static void main(String[] args) {
        Main.main(args);
    }

    private static void test() {
        try {
            FileReader reader = new FileReader(Objects.requireNonNull(MainExecutor.class.getResource("/files/test3.json")).getFile(), StandardCharsets.UTF_8);
            /*StringBuilder builder = new StringBuilder();
               Scanner src = new Scanner(reader);
            while(src.hasNext())
                builder.append(src.next());
            System.out.println(builder.);*/
            JsonArray array = new Gson().fromJson(reader,JsonArray.class);
            boolean skip = false;
            for(int i=0;i< array.size();i++)
            {
                if(skip)
                {
                    skip = false;
                    continue;
                }
                String str = array.get(i).getAsString();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
