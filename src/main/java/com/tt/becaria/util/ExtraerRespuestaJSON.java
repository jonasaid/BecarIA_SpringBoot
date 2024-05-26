package com.tt.becaria.util;

public class ExtraerRespuestaJSON {
    public static String extractAnswerFromResponse(String jsonResponse) {
        String key = "\"answer\":\"";
        int startIndex = jsonResponse.indexOf(key) + key.length();
        if (startIndex == key.length() - 1) {
            return null; // Key no encontrada
        }
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        if (endIndex == -1) {
            return null; // No se encontró el fin del valor
        }
        return jsonResponse.substring(startIndex, endIndex);
    }

}
