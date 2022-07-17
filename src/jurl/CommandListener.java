package jurl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

/**
 * CommandListener class analyses commands and builds request model
 *
 * @author ZK
 */
public class CommandListener {

    private String[] args;
    private Printer printer;
    private Request request;
    private boolean isCorrect;

    /**
     * create command listener to analyse input commands
     *
     * @param args commands of program
     */
    public CommandListener(String[] args) {
        this.args = args;
        isCorrect = true;
        printer = new Printer();
        analyse();
    }

    /**
     * analyse and create request if it is correct
     */
    private void analyse() {
        if (args.length == 0) {
            printer.printNoArgsMessage();
            return;
        }
        // search for help command
        for (String arg : args) {
            if (arg.equals("--help") || arg.equals("-h")) {
                printer.printHelpMessage();
                return;
            }
        }
        // search for list command
        for (String arg : args) {
            if (arg.equals("list")) {
                if (FileUtils.getNumOfSavedRequests() == 0)
                    printer.printNoRequestMessage();
                else
                    printer.printList(Objects.requireNonNull(FileUtils.readAllRequests()));
                return;
            }
        }
        if (args[0].equals("fire")) {
            fireRequest();
            return;
        }
        createRequest();
    }

    /**
     * resend a saved request
     */
    public void fireRequest() {
        int[] indexes = new int[args.length - 1];
        for (int index = 1; index < args.length; index++) {
            for (int i = 0; i < args[index].length(); i++) {
                if (Character.isDigit(args[index].charAt(i))) {
                    indexes[index - 1] = Integer.parseInt(String.valueOf(args[index].charAt(i)));
                } else {
                    isCorrect = false;
                    printer.printRequestIndexError();
                    return;
                }
            }
        }
        for (int index : indexes) {
            if (index > FileUtils.getNumOfSavedRequests() || index == 0) {
                isCorrect = false;
                printer.printRequestIndexError();
                return;
            }
        }
        Request[] requests = FileUtils.readAllRequests();
        for (int index : indexes) {
            System.out.println("\n" + requests[index - 1].getRequestName() + ":");
            sendRequest(requests[index - 1]);
        }
    }

    /**
     * create a new request
     */
    private void createRequest() {
        // search for URL
        if (args[0].startsWith("-")) {
            printer.printURLError();
            return;
        }
        // check number of message body arguments
        int numberOfBodies = 0;
        for (String arg : args) {
            if (arg.equals("-j") || arg.equals("--json") ||
                    arg.equals("-d") || arg.equals("--data") ||
                    arg.equals("--upload"))
                numberOfBodies++;
        }
        if (numberOfBodies > 1) {
            printer.printBodyArgError();
            return;
        }
        // create request
        request = new Request((args[0].startsWith("http://") ? "" : "http://") + args[0]);
        // analyse request option commands
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("-d") || args[i].equals("--data")) {
                request.setFormBody(buildFormData(args[i + 1]));
                i++;
            } else if (args[i].equals("-f") || args[i].equals("--follow")) {
                request.setFollowRedirect(true);
            } else if (args[i].equals("-H") || args[i].equals("--header")) {
                request.setHeaders(buildHeaders(args[i + 1]));
                i++;
            } else if (args[i].equals("-i") || args[i].equals("--include")) {
                request.setShowResponseHeaders(true);
            } else if (args[i].equals("-j") || args[i].equals("--json")) {
                beautify(args[i + 1]);
                i++;
            } else if (args[i].equals("-M") || args[i].equals("--method")) {
                setRequestMethod(args[i + 1]);
                i++;
            } else if (args[i].equals("-O") || args[i].equals("--output")) {
                if (i + 1 >= args.length)
                    setOutput("-i");
                else if (setOutput(args[i + 1]))
                    i++;
            } else if (args[i].equals("-S") || args[i].equals("--save")) {
                setRequestName("");
            } else if (args[i].equals("--upload")) {
                uploadFile(args[i + 1]);
                i++;
            } else {
                printer.printInputError(args[i]);
                isCorrect = false;
            }
        }
        if (isCorrect) {
            sendRequest(request);
            if (request.getRequestName() != null) {
                FileUtils.saveRequest(request);
            }
            if (request.getOutputName() != null) {
                if (request.getResponseBody() != null)
                    FileUtils.saveResponse(request);
                else
                    printer.printNoResponseError();
            }
        }
    }

    /**
     * send a request
     *
     * @param request request to send
     */
    private void sendRequest(Request request) {
        HTTPClient httpClient = new HTTPClient(request, true);
        printer.printResponseInfo(request);
        if (request.isShowResponseHeaders() && request.getResponseHeaders() != null)
            printer.printHeaders(request.getResponseHeaders());
        if (request.getResponseBody() != null) {
            if (request.getResponseHeaders() != null && request.getResponseHeaders().get("Content-Type") != null && request.getResponseHeaders().get("Content-Type").get(0).contains("application/json")) {
                String string = CommandListener.toPrettyFormat(new String(request.getResponseBody()));
                printer.printResponse(string, 100);
            } else {
                printer.printResponse(new String(request.getResponseBody()), 100);
            }
        }
    }

    /**
     * convert given form data in right format if it's possible
     *
     * @param formString the form data string
     * @return built form data
     */
    public HashMap<String, String> buildFormData(String formString) {
        HashMap<String, String> formData;
        if (formString.startsWith("&") || formString.endsWith("&")) {
            printer.printFormBodyError();
            isCorrect = false;
            return null;
        }
        String[] keyValues = formString.split("&");
        formData = new HashMap<>();
        for (String string : keyValues) {
            if (countOccurrences(string, '=') != 1) {
                printer.printFormBodyError();
                isCorrect = false;
                return null;
            }
            String key;
            String value;
            if (string.equals("=")) {
                key = "";
                value = "";
            } else if (string.startsWith("=")) {
                key = "";
                value = string.replace("=", "").trim();
            } else if (string.endsWith("=")) {
                key = string.replace("=", "").trim();
                value = "";
            } else {
                String[] keyValue = string.split("=");
                key = keyValue[0].trim();
                value = keyValue[1].trim();
            }
            formData.put(key, value);
        }
        return formData;
    }

    /**
     * build request headers in HashMap format
     *
     * @param headerString headers string format
     * @return headers in HashMap format
     */
    public HashMap<String, String> buildHeaders(String headerString) {
        HashMap<String, String> headers;
        if (headerString.startsWith(";") || headerString.endsWith(";")) {
            printer.printHeaderError();
            isCorrect = false;
            return null;
        }
        String[] keyValues = headerString.split(";");
        headers = new HashMap<>();
        for (String string : keyValues) {
            if (countOccurrences(string, ':') != 1) {
                printer.printHeaderError();
                isCorrect = false;
                return null;
            }
            String key;
            String value;
            if (string.equals(":")) {
                key = "";
                value = "";
            } else if (string.startsWith(":")) {
                key = "";
                value = string.replace(":", "").trim();
            } else if (string.endsWith(":")) {
                key = string.replace(":", "").trim();
                value = "";
            } else {
                String[] keyValue = string.split(":");
                key = keyValue[0].trim();
                value = keyValue[1].trim();
            }
            headers.put(key, value);
        }
        return headers;
    }

    /**
     * beautify JSON text
     *
     * @param jsonBody json text to convert
     */
    public void beautify(String jsonBody) {
        if (jsonBody == null || jsonBody.trim().length() == 0)
            return;
        try {
            // here toPrettyFormat method is used
            String pretty = toPrettyFormat(jsonBody);
            request.setJsonBody(pretty);
        } catch (Exception e) {
            isCorrect = false;
            Printer.printJsonBodyError();
        }
    }

    /**
     * Convert a JSON string to pretty print version
     *
     * @param jsonString the json string that we want to convert
     * @return prettyJson
     */
    public static String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        try {
            JsonObject json = parser.parse(jsonString).getAsJsonObject(); // getAsJsonObject() returns JsonObject
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(json);
        } catch (Exception e) {
            Printer.printJsonBodyError();
        }
        return null;
    }

    /**
     * set type of HTTP method
     *
     * @param method name ofd method
     */
    public void setRequestMethod(String method) {
        if (!(method.equals("GET") || method.equals("POST") || method.equals("PUT") || method.equals("DELETE"))) {
            printer.printMethodError();
            isCorrect = false;
            return;
        }
        request.setMethod(method);
    }

    /**
     * set the output name
     *
     * @param output name of output file
     * @return true if given name was used
     */
    public boolean setOutput(String output) {
        if (output.equals("-d") || output.equals("--data") ||
                output.equals("-f") || output.equals("--follow") ||
                output.equals("-H") || output.equals("--header") ||
                output.equals("-i") || output.equals("--include") ||
                output.equals("-j") || output.equals("--json") ||
                output.equals("-M") || output.equals("--method") ||
                output.equals("-O") || output.equals("--output") ||
                output.equals("-S") || output.equals("--save") ||
                output.equals("--upload")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            request.setOutputName("output_" + formatter.format(LocalDateTime.now()));
            return false;
        }
        request.setOutputName(output);
        return true;
    }

    /**
     * upload a file
     *
     * @param filePath file address
     */
    public void uploadFile(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.exists(path) && Files.isRegularFile(path)) {
            File fileToUpload = new File(filePath);
            request.setBinaryFile(fileToUpload);
        } else {
            isCorrect = false;
            printer.printBinaryBodyError();
        }
    }

    /**
     * set name of request to be saved
     *
     * @param string name
     */
    public void setRequestName(String string) {
        if (string.length() > 0) {
            request.setRequestName(string);
            return;
        }
        String name = "request_";
        try {
            name += (Objects.requireNonNull(new File("./requests/" + File.separator).list()).length + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setRequestName(name);
    }

    /**
     * count number of occurrences a char in a string
     *
     * @param string  the sting the searching is done within it
     * @param toCount char to search for
     * @return number of occurrences
     */
    private int countOccurrences(String string, char toCount) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == toCount) {
                count++;
            }
        }
        return count;
    }
}