package jurl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * HTTP class gets a request object and sends http request based on that
 *
 * @author ZK
 */
public class HTTPClient {
    private Request request;
    private boolean workOnConsole;


    /**
     * create http client object with given request
     *
     * @param request request
     * @param workOnConsole to print message on console or not
     */
    public HTTPClient(Request request, boolean workOnConsole) {
        this.workOnConsole = workOnConsole;
        setRequest(request);

    }

    /**
     * set request and other fields
     *
     * @param request request to set
     */
    public void setRequest(Request request) {
        this.request = request;
        initialize();
    }

    /**
     * initialize the connection
     */
    private void initialize() {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(request.getUrl());
            long start = System.currentTimeMillis();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.getMethod());
            setFollowRedirect(connection);
            if (request.getHeaders() != null && request.getHeaders().size() != 0)
                setHeaders(connection, request.getHeaders());
            if (request.getFormBody() != null && request.getFormBody().size() != 0)
                formData(connection, request.getFormBody());
            if (request.getJsonBody() != null && request.getJsonBody().length() != 0)
                jsonBody(connection, request.getJsonBody());

            if (request.getBinaryFile() != null && request.getBinaryFile().length() != 0)
                uploadBinary(connection, request.getBinaryFile());
            readResponse(connection);
            connection.disconnect();
            long end = System.currentTimeMillis();
            request.setTimePassed((end - start) + " ms");
        } catch (IOException e) {
            if (workOnConsole) {
                Printer.printException(e);
            }
            setException(e);
        }
    }

    /**
     * set exception properties
     *
     * @param e exception
     */
    public void setException(Exception e) {
        if (!e.toString().contains("4") && !e.toString().contains("5")) {
            request.setResponseMessage("Error");
            request.setResponseHeaders(null);
        }
        String exp = e.toString();
        exp = "Exception -> " + exp;
        request.setResponseBody(exp.getBytes());
    }

    /**
     * set that request follows redirect or not
     *
     * @param connection http connection
     */
    public void setFollowRedirect(HttpURLConnection connection) {
        connection.setInstanceFollowRedirects(request.isFollowRedirect());
    }

    /**
     * create multipart/form data body
     *
     * @param body                 message body
     * @param boundary             the boundary
     * @param bufferedOutputStream output stream
     * @throws IOException caused by working with streams
     */
    public void bufferOutFormData(HashMap<String, String> body, String boundary, BufferedOutputStream bufferedOutputStream) throws IOException {
        for (String key : body.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file")) {
                bufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + (new File(body.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                try {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(body.get(key))));
                    byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                    bufferedOutputStream.write(filesBytes);
                    bufferedOutputStream.write("\r\n".getBytes());
                } catch (IOException e) {
                    if (workOnConsole) {
                        Printer.printException(e);
                    }
                    setException(e);
                }
            } else {
                bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
            }
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    /**
     * set multipart/form data message body
     *
     * @param connection http connection
     * @param form       body
     */
    public void formData(HttpURLConnection connection, HashMap<String, String> form) {
        try {
            String boundary = System.currentTimeMillis() + "";
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            BufferedOutputStream request = new BufferedOutputStream(connection.getOutputStream());
            bufferOutFormData(form, boundary, request);
        } catch (Exception e) {
            if (workOnConsole) {
                Printer.printException(e);
            }
            setException(e);
        }
    }

    /**
     * upload a binary file
     *
     * @param connection http connection
     * @param file       file to upload
     */
    public void uploadBinary(HttpURLConnection connection, File file) {
        try {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(connection.getOutputStream());
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedOutputStream.write(fileInputStream.readAllBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e) {
            if (workOnConsole) {
                Printer.printException(e);
            }
            setException(e);
        }
    }

    /**
     * set json message body
     *
     * @param connection      http connection
     * @param jsonInputString json text
     */
    public void jsonBody(HttpURLConnection connection, String jsonInputString) {
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            if (workOnConsole) {
                Printer.printException(e);
            }
            setException(e);
        }
    }

    /**
     * set request headers
     *
     * @param connection http connection
     * @param headers    request headers to set
     */
    public void setHeaders(HttpURLConnection connection, HashMap<String, String> headers) {
        for (String key : headers.keySet())
            connection.setRequestProperty(key, headers.get(key));
    }

    /**
     * read response from server
     *
     * @param connection http connection
     */
    public void readResponse(HttpURLConnection connection) {
        int size = 0;
        try {
            request.setResponseMessage(connection.getResponseCode() + " " + connection.getResponseMessage());
            request.setResponseHeaders(connection.getHeaderFields());
            InputStream in = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.flush();
            out.close();
            in.close();
            request.setResponseBody(out.toByteArray());
            size = request.getResponseBody().length;
        } catch (IOException e) {
            if (workOnConsole) {
                Printer.printException(e);
            }
            setException(e);
        } finally {
            if (size < 1024)
                request.setDataSize(size + " B");
            else if (size < (1024 * 1024))
                request.setDataSize((size / 1024) + " KB");
            else
                request.setDataSize((size / (1024 * 1024)) + " MB");
        }
    }

    /**
     * get request
     *
     * @return request field
     */
    public Request getRequest() {
        return request;
    }

}