package com.twilio.appointmentreminders.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class RequestHandler {
  public final static int GET = 1;
  public final static int POST = 2;
  static String response = null;

  public RequestHandler() {

  }

  public String makeServiceCall(String url, int method) {
    return this.makeServiceCall(url, method, null);
  }

  public String makeServiceCall(String url, int method, List<NameValuePair> params) {
    try {
      // http client
      HttpParams my_httpParams = new BasicHttpParams();
      HttpConnectionParams.setConnectionTimeout(my_httpParams, 15000);
      HttpConnectionParams.setSoTimeout(my_httpParams, 15000);
      DefaultHttpClient httpClient = new DefaultHttpClient(my_httpParams);
      HttpEntity httpEntity = null;
      HttpResponse httpResponse = null;

      // Checking http request method type
      if (method == POST) {
        HttpPost httpPost = new HttpPost(url);
        // adding post params
        if (params != null) {
          httpPost.setEntity(new UrlEncodedFormEntity(params));
        }

        httpResponse = httpClient.execute(httpPost);

      } else if (method == GET) {
        // appending params to url
        if (params != null) {
          String paramString = URLEncodedUtils.format(params, "utf-8");
          url += "?" + paramString;
        }
        HttpGet httpGet = new HttpGet(url);

        httpResponse = httpClient.execute(httpGet);

      }
      httpEntity = httpResponse.getEntity();
      response = EntityUtils.toString(httpEntity);

    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    } catch (ClientProtocolException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    return response;
  }
}

