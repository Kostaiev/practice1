package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * The class, depending on the selected system parameter “-Dformat=”json/xml“”,
 * sends the message “Hello `name`!” to the console in XML or JSON format.
 * The program receives the username from the file 'message.properties',
 * which should be located near the Jar file or you need to pass the file path to the program arguments.
 * If the format parameter is not passed, json will be the default.
 * Example message:
 * { “message”: “Hello <text from external properties file, username=your name> !"}
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Start program------------------------------>");

        String printFormat = System.getProperty("format", "xml").toLowerCase();
        logger.info("Print format: " + printFormat);
        logger.debug("set file printFormat: " + printFormat);

        String username = getProperty("username", args);
        logger.debug("set username: " + username);

        Message message = new Message("Hello " + username + "!");
        logger.info(message.toString());
        logger.debug("new Message object: " + message);

        switch (printFormat) {
            case "json": {
                printJson(message);
                logger.debug("switch json parameter");
                break;
            }
            case "xml": {
                printXml(message);
                logger.debug("switch xml parameter");
                break;
            }
            default: {
                printJson(message);
                logger.debug("switch default parameter");
            }
        }
        logger.info("<------------------------------End program");
    }

    /**
     * Finds the .properties file and sets the requested parameter.
     *
     * @param name parameter name
     * @return parameter value
     */
    private static String getProperty(String name, String[] args) {
        String configPath = "message.properties";

        //use export CONFIG=HelloWorld to set value in console for one session
        if (args.length > 0) {
            configPath = args[0];
            logger.debug("args has a path to properties: " + configPath);
        } else if (System.getenv("CONFIG") != null) {
            configPath = System.getenv("CONFIG");
            logger.debug("environment variable has a path to properties");
        }
        Properties configProperties = new Properties();

        File file = new File(configPath);

        logger.debug("Is properties file exists:" + file.exists());

        try (InputStream input = file.exists() ? new FileInputStream(file) : Thread.currentThread().getContextClassLoader().getResourceAsStream(configPath)) {

            configProperties.load(new InputStreamReader(input, StandardCharsets.UTF_8));

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return configProperties.getProperty(name, "default");
    }

    /**
     * Prints an object converted to JSON format in the console
     *
     * @param obj a object to be converted
     */
    private static void printJson(Message obj) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(obj);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        logger.debug("Json file was printed");
    }

    /**
     * Prints an object converted to XML format in the console
     *
     * @param obj object to be converted
     */
    private static void printXml(Message obj) {
        XmlMapper xmlMapper = new XmlMapper();

        try {
            String xml = xmlMapper.writeValueAsString(obj);
            System.out.println(xml);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        logger.info("Xml file was printed");
    }
}