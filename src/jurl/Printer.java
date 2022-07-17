package jurl;

import java.util.List;
import java.util.Map;

/**
 * Printer class does all printing stuff
 *
 * @author ZK
 */
public class Printer {

    /**
     * prints massage when no args are passed to program
     */
    public void printNoArgsMessage() {
        System.out.println("jurl : try '--help' for more information.");
    }

    /**
     * print help for commands
     */
    public void printHelpMessage() {
        System.out.println("Usage: Jurl <url> [options...]\n" +
                " -d, --data <data>  HTTP POST data\n" +
                " -f, --follow       Follow redirect from server\n" +
                " -j, --json <jsonBody>     Set json message body\n" +
                " -H, --header <header>  Pass custom header(s) to server\n" +
                " -h, --help         This help text\n" +
                " -i, --include      Include protocol response headers in the output\n" +
                " -M, --method       Set the http request method(GET is default)\n" +
                " -O, --output [filename]   Save the response body in a file\n" +
                " -S, --save       Save the request and all it's settings\n" +
                "     --upload <@file>   Upload a file on sever");
    }

    /**
     * print list of saved requests
     *
     * @param requests request to be printed
     */
    public void printList(Request[] requests) {
        int i = 1;
        for (Request request : requests) {
            System.out.print(i + " . " +
                    "url: " + request.getUrl() + " | " +
                    "method: " + request.getMethod() + " | headers: ");
            if (request.getHeaders() == null || request.getHeaders().size() == 0)
                System.out.println("-");
            else {
                for (String key : request.getHeaders().keySet()) {
                    System.out.print(key + ":" + request.getHeaders().get(key) + " ; ");
                }
                System.out.print("\b\b\b\n");
            }
            i++;
        }
    }

    /**
     * print error because url is not specified
     */
    public void printURLError() {
        System.out.println("jurl: no URL specified!");
    }

    /**
     * print error when there are more than one message body
     */
    public void printBodyArgError() {
        System.out.println("jurl: request can only have one type of message body!");
    }

    /**
     * print error for wrong inputs
     *
     * @param arg false command
     */
    public void printInputError(String arg) {
        System.out.println("jurl : option \"" + arg + "\": is unknown");
    }

    /**
     * print error if form data does not have correct format
     */
    public void printFormBodyError() {
        System.out.println("jurl: Given form data doesn't have right format.");
    }

    /**
     * print error if json body does not have correct format
     */
    public static void printJsonBodyError() {
        System.out.println("jurl: Given json body doesn't have right format.");
    }

    /**
     * print error if headers does not have correct format
     */
    public void printHeaderError() {
        System.out.println("jurl: Given header doesn't have right format.");
    }

    /**
     * print error when given method is wrong
     */
    public void printMethodError() {
        System.out.println("jurl: Given method is not supported.");
    }

    /**
     * print error when given file address does not exist or id a directory
     */
    public void printBinaryBodyError() {
        System.out.println("jurl: Couldn't find file to upload.");
    }

    /**
     * print headers in a good format
     *
     * @param headers headers to be printed
     */
    public void printHeaders(Map<String, List<String>> headers) {
        for (String key : headers.keySet()) {
            System.out.print(key + " : ");
            List<String> values = headers.get(key);
            for (int i = 0; i < values.size(); i++) {
                System.out.print(values.get(i));
                if (i == values.size() - 1)
                    System.out.print("\n");
                else
                    System.out.print(" | ");
            }
        }
    }

    /**
     * set headers in a string format
     *
     * @param headers headers to convert
     * @return formatted string
     */
    public static String getResponseHeadersStr(Map<String, List<String>> headers) {
        String headersStr = "";
        for (String key : headers.keySet()) {
            headersStr += key + " : ";
            List<String> values = headers.get(key);
            for (int i = 0; i < values.size(); i++) {
                headersStr += values.get(i);
                if (i == values.size() - 1)
                    headersStr += "\n";
                else
                    headersStr += " | ";
            }
        }
        return headersStr;
    }

    /**
     * print response body
     *
     * @param body     response body
     * @param lineSize size of each line
     */
    public void printResponse(String body, int lineSize) {
        int index = 0;
        while (index < body.length()) {
            if (index + lineSize < body.length())
                System.out.println(body.substring(index, index + lineSize));
            else
                System.out.println(body.substring(index));
            index += (lineSize + 1);
        }
    }

    /**
     * set response body lines
     *
     * @param body     response body
     * @param lineSize size of each line
     * @return formatted string
     */
    public static String setLines(String body, int lineSize) {
        if (body.length() < 2 * lineSize && body.contains("\n"))
            return body;
        String newBody = "";
        int index = 0;
        while (index < body.length()) {
            if (index + lineSize < body.length())
                newBody += body.substring(index, index + lineSize) + "\n";
            else
                newBody += body.substring(index);
            index += (lineSize + 1);
        }
        return newBody;
    }

    /**
     * print error if given index is wrong
     */
    public void printRequestIndexError() {
        System.out.println("jurl: given index of request is wrong.");
    }

    /**
     * print error if response has no body to save it
     */
    public void printNoResponseError() {
        System.out.println("jurl : No response body to save!");
    }

    /**
     * print message if there is no saved request
     */
    public void printNoRequestMessage() {
        System.out.print("jurl: No Saved Request!");
    }

    /**
     * print information of response
     *
     * @param request http request
     */
    public void printResponseInfo(Request request) {
        System.out.println("Status Message: " + request.getResponseMessage() + "\n" +
                "Time Passed: " + request.getTimePassed() + "\n" + "Data Size: " + request.getDataSize());
    }

    /**
     * get an exception and print it properly in console
     *
     * @param e exception
     */
    public static void printException(Exception e) {
        String exp = e.toString();
        System.out.println("Exception -> " + exp);
    }
}