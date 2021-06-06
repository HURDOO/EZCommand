package kr.kro.ezcommand.engine.parser.file;

import kr.kro.ezcommand.engine.thirdparty.plugin.EZJavaPlugin;
import kr.kro.ezcommand.engine.thirdparty.plugin.EZPluginLoader;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

    public static void loadEZPlugins()
    {
        //temp_loadEZPlugins();
        EZPluginLoader.prepare();
        EZPluginLoader.load();
    }

    private static List<Class<? extends EZJavaPlugin>> temp_loadEZPlugins() {
        try
        {
            String path ="D:\\1_Coding\\EZCommand\\je-1165\\build\\libs\\je-1165-Alpha-0.1.jar";
            JarFile jar = new JarFile(path);
            Enumeration<JarEntry> jarEnum = jar.entries();
            BufferedReader reader = new BufferedReader( new InputStreamReader(
                        jar.getInputStream(jar.getEntry("ezplugin.yml"))
                    ));

            StringBuilder ymlContent = new StringBuilder();
            String s;
            String mainClassPath = null;
            while((s = reader.readLine()) != null) {
                ymlContent.append(s).append("\n");
                if(s.contains("main"))
                {
                    mainClassPath = s.replace("main: ", "").replace("/",".");
                }
            }

            System.out.println(ymlContent);

            URL[] urls = {new URL("jar:file:" + path + "!/")};

            URLClassLoader loader = URLClassLoader.newInstance(urls);
            Thread.currentThread().setContextClassLoader(loader);

            Class<? extends EZJavaPlugin> mainClass = null;
            while (jarEnum.hasMoreElements()) {
                JarEntry je = jarEnum.nextElement();

                if(je.isDirectory() || !je.getName().endsWith(".class")){
                    continue;
                }
                System.out.println(je);
                // -6 because of .class
                String className = je.getName().substring(0,je.getName().length()-6);
                className = className.replace('/', '.');
                if(className.equals(mainClassPath)) mainClass = loader.loadClass(className).asSubclass(EZJavaPlugin.class);
                else loader.loadClass(className);
            }

            System.out.println("a " + mainClassPath);
            mainClass.getMethod("onEnable").invoke(mainClass.getConstructor().newInstance());

        } catch (IOException | ClassNotFoundException | NoSuchMethodException
                | InvocationTargetException | IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
