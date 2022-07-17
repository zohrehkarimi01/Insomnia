package jurl;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request class is the model to save http request details
 *
 * @author ZK
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    private String requestName;
    // request details
    private String method;
    private String url;
    private boolean followRedirect;
    private boolean showResponseHeaders;
    private HashMap<String, String> headers;
    private HashMap<String, String> noneActiveHeaders;
    private HashMap<String, String> formBody;
    private HashMap<String, String> noneActiveFormBody;
    private String jsonBody;
    private File binaryFile;
    private String outputName;
    // response details
    private byte[] responseBody;
    private Map<String, List<String>> responseHeaders;
    private String responseMessage;
    private String timePassed;
    private String dataSize;

    /**
     * create a http request
     *
     * @param url the URL (address of server)
     */
    public Request(String url) {
        this.url = url;
        method = "GET";
        followRedirect = false;
        showResponseHeaders = false;
        headers = new HashMap<>();
        noneActiveHeaders = new HashMap<>();
        formBody = new HashMap<>();
        noneActiveFormBody = new HashMap<>();
        jsonBody = "";
        binaryFile = null;
        outputName = null;
        requestName = null;
        responseBody = null;
        responseHeaders = null;
        responseMessage = "";
        timePassed = "0 ms";
        dataSize = "0 B";
    }

    /**
     * set name of request
     *
     * @param requestName name
     */
    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    /**
     * set method of http request
     *
     * @param method method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * set server URL
     *
     * @param url url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * set the request to follow redirect of not
     *
     * @param followRedirect true or false
     */
    public void setFollowRedirect(boolean followRedirect) {
        this.followRedirect = followRedirect;
    }

    /**
     * set to show response headers or not
     *
     * @param showResponseHeaders true pr false
     */
    public void setShowResponseHeaders(boolean showResponseHeaders) {
        this.showResponseHeaders = showResponseHeaders;
    }

    /**
     * set none active Headers of request
     *
     * @param noneActiveHeaders none active headers to set
     */
    public void setNoneActiveHeaders(HashMap<String, String> noneActiveHeaders) {
        this.noneActiveHeaders = noneActiveHeaders;
    }

    /**
     * set form body message none active
     *
     * @param noneActiveFormBody none active form body
     */
    public void setNoneActiveFormBody(HashMap<String, String> noneActiveFormBody) {
        this.noneActiveFormBody = noneActiveFormBody;
    }

    /**
     * set Headers of request
     *
     * @param headers headers to set
     */
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    /**
     * set form body message
     *
     * @param formBody input form body
     */
    public void setFormBody(HashMap<String, String> formBody) {
        this.formBody = formBody;
    }

    /**
     * set json body o =f request
     *
     * @param jsonBody json body
     */
    public void setJsonBody(String jsonBody) {
        this.jsonBody = jsonBody;
    }

    /**
     * set a binary file for message body
     *
     * @param binaryFile given file
     */
    public void setBinaryFile(File binaryFile) {
        this.binaryFile = binaryFile;
    }

    /**
     * set name of response output to save
     *
     * @param outputName name
     */
    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    /**
     * set response body
     *
     * @param responseBody response body to set
     */
    public void setResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }

    /**
     * set response headers
     *
     * @param responseHeaders headers to set
     */
    public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    /**
     * set response message
     *
     * @param responseMessage response message
     */
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    /**
     * set time passed
     *
     * @param timePassed time to set
     */
    public void setTimePassed(String timePassed) {
        this.timePassed = timePassed;
    }

    /**
     * set size of data read from server
     *
     * @param dataSize size of data
     */
    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    /**
     * get name of request
     *
     * @return name of request
     */
    public String getRequestName() {
        return requestName;
    }

    /**
     * get request's method
     *
     * @return method field
     */
    public String getMethod() {
        return method;
    }

    /**
     * get URL
     *
     * @return url field
     */
    public String getUrl() {
        return url;
    }

    /**
     * get follow redirect property
     *
     * @return followRedirect field
     */
    public boolean isFollowRedirect() {
        return followRedirect;
    }

    /**
     * get show the response headers
     *
     * @return showResponseHeaders filed
     */
    public boolean isShowResponseHeaders() {
        return showResponseHeaders;
    }

    /**
     * get request headers
     *
     * @return headers field
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * get form message body
     *
     * @return formBody field
     */
    public HashMap<String, String> getFormBody() {
        return formBody;
    }

    /**
     * get json message body
     *
     * @return jsonBody field
     */
    public String getJsonBody() {
        return jsonBody;
    }

    /**
     * get file to upload
     *
     * @return binaryFile filed
     */
    public File getBinaryFile() {
        return binaryFile;
    }

    /**
     * get response body
     *
     * @return responseBody field
     */
    public byte[] getResponseBody() {
        return responseBody;
    }

    /**
     * get name of response to save
     *
     * @return outputName filed
     */
    public String getOutputName() {
        return outputName;
    }

    /**
     * get response headers
     *
     * @return responseHeaders field
     */
    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * get response message and code
     *
     * @return responseMessage field
     */
    public String getResponseMessage() {
        return responseMessage;
    }

    /**
     * get time passed to send request
     *
     * @return timePassed field
     */
    public String getTimePassed() {
        return timePassed;
    }

    /**
     * get size of data read from server
     *
     * @return dataSize field
     */
    public String getDataSize() {
        return dataSize;
    }

    /**
     * get  none active response headers
     *
     * @return noneActiveHeaders field
     */
    public HashMap<String, String> getNoneActiveHeaders() {
        return noneActiveHeaders;
    }

    /**
     * get none active form message body
     *
     * @return noneActiveFormBody field
     */
    public HashMap<String, String> getNoneActiveFormBody() {
        return noneActiveFormBody;
    }
}