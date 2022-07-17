package gui;

import jurl.FileUtils;
import jurl.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * AppData class keeps some data that app needs to work properly.
 *
 * @author ZK
 */
public class AppData {

    // app settings
    private Settings settings;
    // request Collection
    private HashMap<String, Request> requestList;
    private Request requestInCharge;

    /**
     * create app data
     */
    public AppData() {
        settings = FileUtils.readSettings();
        requestInCharge = null;
        requestList = new HashMap<>();
        updateList();
    }

    /**
     * initialize requests list
     */
    private void updateList() {
        Request[] requests = FileUtils.readAllRequests();
        for (Request request : requests) {
            String name = "     " + request.getRequestName();
            requestList.put(name, request);
        }
    }

    /**
     * @return requestList field
     */
    public Map<String, Request> getRequestList() {
        return requestList;
    }

    /**
     * set request in charge
     *
     * @param requestInCharge to set requestInCharge
     */
    public void setRequestInCharge(Request requestInCharge) {
        this.requestInCharge = requestInCharge;
    }

    /**
     * get request in charge
     *
     * @return requestInCharge
     */
    public Request getRequestInCharge() {
        return requestInCharge;
    }

    /**
     * get settings of app
     *
     * @return settings field
     */
    public Settings getSettings() {
        return settings;
    }
}