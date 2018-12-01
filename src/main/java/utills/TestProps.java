package utills;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProps {
    private final Properties properties = new Properties();
    private static TestProps INSTANCE = null;

    private TestProps(){
        try{
            properties.load(new FileInputStream(new File("./src/test/resources/configuration/application.properties")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static TestProps getInstance(){
        if (INSTANCE == null){
            INSTANCE = new TestProps();
        }
        return INSTANCE;
    }

    public Properties getProperties(){
        return properties;
    }
}
