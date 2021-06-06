package kr.kro.ezcommand.engine.thirdparty.plugin;

import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class EZPluginYMLParser {

    public static EZPluginYML parse(InputStream input)
    {
        Yaml parser = new Yaml();
        Map<String, Object> yaml = parser.load(input);

        String name = yaml.get("name").toString();
        String main = yaml.get("main").toString();
        String version = yaml.get("version").toString();
        String plugin_code = yaml.get("plugin-code").toString();
        String api_version = yaml.get("api-version").toString();

        assert name != null;
        assert main != null;
        assert version != null;
        assert plugin_code != null;
        assert api_version != null;

        //description = yaml.get("description").toString();
        //author = yaml.get("author").toString();

        EZPluginYML yml = new EZPluginYML(name, main, version, plugin_code, api_version, null, null, null, null);
        return yml;
    }
}
