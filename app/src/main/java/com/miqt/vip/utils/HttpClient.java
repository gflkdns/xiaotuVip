package com.miqt.vip.utils;

import com.miqt.wand.anno.AddToFixPatch;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author miqt
 */
@AddToFixPatch
public class HttpClient {
    String url;
    int timeOut = 5000;
    boolean useCaches;
    String method = "GET";
    Map<String, String> qMap;
    Map<String, String> bMap;
    Map<String, String> headMap;

    String bodyContent;

    private HttpClient(String url) {
        this.url = url;
    }


    public static HttpClient get(String url) {
        return new HttpClient(url);
    }


    public HttpClient setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public HttpClient setUseCaches(boolean useCaches) {
        this.useCaches = useCaches;
        return this;
    }

    public HttpClient setMethod(String method) {
        this.method = method;
        return this;
    }

    public HttpClient setQueryMap(Map<String, String> qMap) {
        this.qMap = qMap;
        setQuery();
        return this;
    }

    public HttpClient setBodyMap(Map<String, String> bMap) {
        this.bMap = bMap;
        return this;
    }

    public HttpClient setHeaderMap(Map<String, String> headMap) {
        this.headMap = headMap;
        return this;
    }

    public HttpClient setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
        return this;
    }

    public static HttpClient with(String url) {
        return new HttpClient(url);
    }


    private void setHttps(HttpURLConnection connection) throws NoSuchAlgorithmException, KeyManagementException {
        HttpsURLConnection con = (HttpsURLConnection) connection;
        //信任所有
        X509TrustManager x509mgr = new X509TrustManager() {

            //　　该方法检查客户端的证书，若不信任该证书则抛出异常
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            // 　　该方法检查服务端的证书，若不信任该证书则抛出异常
            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            // 　返回受信任的X509证书数组。
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{x509mgr}, null);
        ////创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
        con.setSSLSocketFactory(sslContext.getSocketFactory());
        con.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    public Resp happy() {
        StringBuilder res = new StringBuilder();
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();

            if (url.startsWith("https")) {
                setHttps(connection);
            }
            connection.setConnectTimeout(timeOut);
            connection.setReadTimeout(timeOut);
            connection.setRequestMethod(method);
            setHeader(connection);

            connection.connect();

            setBody(connection);

            int response_code = connection.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    res.append(line);
                }
                reader.close();
                connection.disconnect();
            }
            return new Resp(response_code, res.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Resp(-1, "");
    }

    private void setBody(HttpURLConnection connection) {
        try {
            StringBuffer params = new StringBuffer();
            if (bodyContent != null) {
                params.append(bodyContent);
            }
            String p;
            if (bMap != null) {
                for (Map.Entry<String, String> entry : bMap.entrySet()) {
                    params.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                            .append("&");
                }
                p = params.substring(0, params.length() - 1);
            } else {
                p = params.substring(0, params.length());
            }
            if (params.length() > 0) {
                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                dos.writeBytes(p);
                dos.flush();
                dos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setQuery() {
        if (qMap == null) {
            return;
        }
        try {
            StringBuffer params = new StringBuffer(url);
            for (Map.Entry<String, String> entry : qMap.entrySet()) {
                params.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append("&");
            }
            if (params.length() > 0) {
                url = params.substring(0, params.length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHeader(HttpURLConnection connection) {
        if (headMap == null) {
            return;
        }
        for (String key : headMap.keySet()) {
            connection.setRequestProperty(
                    key,
                    headMap.get(key));
        }
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    }

    public static class Resp {
        public int code;
        public String content;

        public Resp(int code, String content) {
            this.code = code;
            this.content = content;
        }
    }

}


