package by.htp.ex.controller.impl.utilities;

import java.util.Map;

public class Usability {
    /**
     * @param requestName - currently (String)session.getAttribute("last_request_name")
     * @param requestParams - currently (Map<String, String[]>) session.getAttribute("last_request_params");
     * @return last request or null if something goes wrong
     */
    public static String getRequest(String requestName, Map<String, String[]> requestParams) {
        StringBuilder lastRequest = new StringBuilder(requestName);
        try {
            Map<String, String[]> paramsMap = requestParams;
            lastRequest.append("?");
            for (var paramArray : paramsMap.entrySet()) {
                for(var param : paramArray.getValue()) {
                    lastRequest.append(paramArray.getKey()).append("=").append(param).append("&");
                }
            }
            lastRequest.deleteCharAt(lastRequest.length()-1);
            return lastRequest.toString();
        } catch (Exception e) {
            return null;
        }

    }
}
