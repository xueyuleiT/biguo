/*
 * Main.java
 * classes : com.jdk6.test.Main
 * @author zenghui
 * V 1.0.0
 * Create at 2015-12-14 下午3:31:09
 */
package com.jdk6.test;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;

public class Jdk6HttpClient {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

        if (args.length == 0) {
            return;
        }

        if (args.length == 3) {
            System.setProperty("java.net.ssl.trustStore", args[2]);
        }

        String url = args[0];
        // String url = "https://test-qhzx.pingan.com.cn:5443";
        try {
            URI uri = new URI(url, true);
            // 创建DefaultHttpClient对象
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(uri.getURI());
            method.setRequestHeader("Content-Type", "application/json;charset=utf-8");

            if (args.length == 2)
                method.setRequestBody(args[1]);

            client.executeMethod(method);
            method.getResponseBody();
            JSONObject json = null;
            if (method.getStatusCode() == 200) {
                json = new JSONObject();
                json.put("status", method.getStatusCode());
                json.put("result", new String(method.getResponseBody()));
            } else {
                json = new JSONObject();
                json.put("status", method.getStatusCode());
                json.put("result", method.getStatusLine());
            }
            System.out.println(json.toString());
        } catch (IOException e) {
            JSONObject json = new JSONObject();
            json.put("status", 1000);
            json.put("result", e.getMessage());
            System.out.println(json.toString());
        }

    }

}
