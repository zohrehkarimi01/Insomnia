package jurl;

import gui.Settings;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * FileUtils class manages working with files
 *
 * @author ZK
 */
public class FileUtils {

    public static final String REQUESTS_PATH = "./requests/";
    public static final String RESPONSES_PATH = "./responses/";
    public static final String SETTINGS_PATH = "./Setting/setting";

    /**
     * save a request in request folders
     *
     * @param request request to save
     */
    public static void saveRequest(Request request) {
        try (FileOutputStream fo = new FileOutputStream(REQUESTS_PATH + File.separator + request.getRequestName())) {
            ObjectOutputStream os = new ObjectOutputStream(fo);
            os.writeObject(request);
            fo.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read all saved requests in request folder
     *
     * @return requests array
     */
    public static Request[] readAllRequests() {
        File[] newFiles = new File(REQUESTS_PATH).listFiles();
        if (newFiles == null) {
            return null;
        }
        Request[] requests = new Request[newFiles.length];
        int i = 0;
        for (File file : newFiles) {
            Request request = null;
            try (FileInputStream fi = new FileInputStream(file)) {
                ObjectInputStream os = new ObjectInputStream(fi);
                request = (Request) os.readObject();
                fi.close();
                os.close();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            requests[i] = request;
            i++;
        }
        return requests;
    }

    /**
     * save response body of request
     *
     * @param request request to save it's response
     */
    public static void saveResponse(Request request) {
        String title;
        if (request.getOutputName().startsWith("output_")) {
            List<String> value = request.getResponseHeaders().get("Content-Type");
            if (value == null) {
                title = request.getOutputName();
            } else if (value.get(0).contains("application/json")) {
                title = request.getOutputName() + ".txt";
                String string = CommandListener.toPrettyFormat(new String(request.getResponseBody()));
                request.setResponseBody(string.getBytes());
            } else if (value.get(0).contains("text/html")) {
                title = request.getOutputName() + ".html";
            } else if (value.get(0).contains("image/png")) {
                title = request.getOutputName() + ".png";
            } else if (value.get(0).contains("text/plain")) {
                title = request.getOutputName() + ".txt";
            } else {
                title = request.getOutputName();
            }
        } else {
            title = request.getOutputName();
        }
        byte[] contentBytes = request.getResponseBody();
        try (FileOutputStream fo = new FileOutputStream(RESPONSES_PATH + File.separator + title)) {
            fo.write(contentBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete a saved request
     *
     * @param name name of request to delete
     */
    public static void deleteRequest(String name) {
        File file = new File(REQUESTS_PATH + File.separator + name);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * read app settings
     *
     * @return settings
     */
    public static Settings readSettings() {
        File file = new File(SETTINGS_PATH + File.separator);
        Settings settings = new Settings();
        if (file.exists()) {
            try (FileInputStream fi = new FileInputStream(file)) {
                ObjectInputStream os = new ObjectInputStream(fi);
                settings = (Settings) os.readObject();
                fi.close();
                os.close();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            return settings;
        }
        return settings;
    }

    /**
     * save setting of app
     *
     * @param settings setting to save
     */
    public static void saveSettings(Settings settings) {
        try (FileOutputStream fo = new FileOutputStream(SETTINGS_PATH + File.separator)) {
            ObjectOutputStream os = new ObjectOutputStream(fo);
            os.writeObject(settings);
            fo.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get number of requests in requests folder
     *
     * @return number of saved requests
     */
    public static int getNumOfSavedRequests() {
        return Objects.requireNonNull(new File(REQUESTS_PATH).listFiles()).length;
    }
}