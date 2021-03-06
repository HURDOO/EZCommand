package kr.kro.ezcommand.engine.thirdparty.plugin;

import kr.kro.ezcommand.EZCommand;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

public class EZPluginLoader {
    private static final List<PreparedEZPlugin> preparedPlugins = new LinkedList<>();

    public static void prepare() {
        File dir = new File(EZCommand.EZPluginFolder);

        Validate.notNull(dir,"cannot find EZPlugin folder");
        if(!dir.isDirectory())
        {
            try {
                throw new IllegalAccessException(dir.getPath() + " is not a directory");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for(File file : Objects.requireNonNull(dir.listFiles()))
        {
            try {
                if (file.isDirectory() || !file.getName().endsWith(".jar")) continue;
                JarFile jarFile = new JarFile(file);
                jarFile.getEntry("ezplugin.yml");
                InputStream yml = jarFile.getInputStream(jarFile.getEntry("ezplugin.yml"));
                PreparedEZPlugin prePlugin = new PreparedEZPlugin(yml, file.getPath());
                preparedPlugins.add(prePlugin);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void load() {

        List<URL> jarPaths = new LinkedList<>();
        for(PreparedEZPlugin prePlugin : preparedPlugins)
        {
            try {
                jarPaths.add(new URL("jar:file:" + prePlugin.jarPath + "!/"));
            } catch (MalformedURLException e) {
                Logger.getGlobal().log(Level.WARNING,"unknown url of prepared ezplugin jar file : " + prePlugin.jarPath);
                preparedPlugins.remove(prePlugin);
                e.printStackTrace();
            }
        }

        URLClassLoader loader = new URLClassLoader(jarPaths.toArray(new URL[0]));
        Thread.currentThread().setContextClassLoader(loader);

        for(PreparedEZPlugin prePlugin : preparedPlugins)
        {
            EZPlugin plugin = null;
            try {
                plugin = EZPluginYMLParser.parse(prePlugin.yml);
            } catch (FileNotFoundException e) {
                Logger.getGlobal().log(Level.WARNING,"Exception while getting input of yml: " + prePlugin.jarPath);
                e.printStackTrace();
                continue;
            }
            Logger.getGlobal().log(Level.FINEST,"Loading plugin " + plugin.getPluginCode() + " v." + plugin.getVersion());

            try {
                loader.loadClass(plugin.getMain()).asSubclass(EZJavaPlugin.class).getConstructor().newInstance().onEnable();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                Logger.getGlobal().log(Level.WARNING,"cannot find main class of ezplugin '" + plugin.getName() + "'");
                e.printStackTrace();
            }
            EZCommand.plugins.add(plugin);
        }
    }
}

class PreparedEZPlugin
{
    InputStream yml;
    String jarPath;

    public PreparedEZPlugin(InputStream yml, String jarPath)
    {
        this.yml = yml;
        this.jarPath = jarPath;
    }
}