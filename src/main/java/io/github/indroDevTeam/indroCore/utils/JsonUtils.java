package io.github.indroDevTeam.indroCore.utils;

import io.github.indroDevTeam.indroCore.IndroCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtils {
    private FileWriter file;
    public Plugin plugin = IndroCore.getPlugin(IndroCore.class);

    public void createFile(String fileName) {
        try {
            File file1 = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + fileName + ".json");
            if (file1.createNewFile()) {
                System.out.println("File created: " + file1.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param obj the Json object to save
     * @param fileName the file name to add without the file extension
     */
    public void saveData(JSONObject obj, String fileName) {
        try {
            file = new FileWriter(plugin.getDataFolder().getAbsolutePath() + File.separator + fileName + ".json");
            file.write(obj.toJSONString());
        } catch (IOException e) {
            Bukkit.getLogger().warning("Unable to save data to JSON File");
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                Bukkit.getLogger().warning("Unable to save data to JSON File");
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void setData(Object key, Object value, String fileName) {
        JSONObject obj = new JSONObject();
        obj.put(key, value);
        saveData(obj, fileName);
    }

    public String getString(String key, String fileName) {
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader reader = new FileReader(
                    plugin.getDataFolder().getAbsolutePath() + File.separator + fileName + ".json");

            //Read JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            if (jsonObject.containsKey(key)) {
                return jsonObject.get(key).toString();
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
