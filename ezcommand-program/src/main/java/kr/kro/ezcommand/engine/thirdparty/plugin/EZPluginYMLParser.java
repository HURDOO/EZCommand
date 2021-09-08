package kr.kro.ezcommand.engine.thirdparty.plugin;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class EZPluginYMLParser {

    public static EZPlugin parse(InputStream input) throws FileNotFoundException {
        Yaml parser = new Yaml();
        Map<String, Object> yaml = parser.load(input);

        String name = yaml.get("name").toString();
        String main = yaml.get("main").toString();
        String version = yaml.get("version").toString();
        String plugin_code = yaml.get("plugin-code").toString();
        String api_version = yaml.get("api-version").toString();

        String description = yaml.get("description").toString();
        String author = yaml.get("author").toString();


        Map<String, String> dependencies = parser.load(parser.dump(yaml.get("dependencies")));
        List<String> dependencyPack = parser.load(parser.dump(dependencies.get("ezpack")));
        List<String> dependencyPlugin = parser.load(parser.dump(dependencies.get("ezplugin")));

        EZPlugin yml = new EZPlugin(name, main, version, plugin_code, api_version,
                description, author, dependencyPack, dependencyPlugin);
        return yml;
    }
}
