package by.htp.ex.controller.util;

import java.util.Map;

public class RequestHelper {
    /**
     * @param requestName - currently (String)session.getAttribute("last_request_name")
     * @param requestParams - currently (Map<String, String[]>) session.getAttribute("last_request_params");
     * @return last request string
     */
    public static String formRequestURL(String requestName, Map<String, String[]> requestParams) {
        StringBuilder lastRequest = new StringBuilder(requestName);

        lastRequest.append("?");
        for (var paramArray : requestParams.entrySet()) {
            for(var param : paramArray.getValue()) {
                lastRequest.append(paramArray.getKey()).append("=").append(param).append("&");
            }
        }
        lastRequest.deleteCharAt(lastRequest.length()-1);
        return lastRequest.toString();


    }
}
