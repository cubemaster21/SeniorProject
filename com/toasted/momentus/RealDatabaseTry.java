package com.toasted.momentus;
import java.util.ArrayList;
import java.util.List;
 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
 

public class RealDatabaseTry {
	 public static void PostToScoreBoard(int score, String level) {
         HttpClient client = new DefaultHttpClient();
         HttpPost post = new HttpPost(
                         "http://ec2-34-207-60-3.compute-1.amazonaws.com:80/db_addscore.php");
         String scoreval = "" + score;

         try {

                 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                 nameValuePairs.add(new BasicNameValuePair("levelID", level));
                 nameValuePairs.add(new BasicNameValuePair("Score_Val", scoreval));
                 

                 post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                 HttpResponse response = client.execute(post);
                 BufferedReader rd = new BufferedReader(new InputStreamReader(
                                 response.getEntity().getContent()));

                 String line = "";
                 while ((line = rd.readLine()) != null) {
                         System.out.println(line);
                         if (line.startsWith("Auth=")) {
                                 String key = line.substring(5);
                                 // do something with the key
                         }

                 }
         } catch (IOException e) {
                 e.printStackTrace();
         }
 }
	 public static void ReadFromScoreBoard(String level) {
         HttpClient client = new DefaultHttpClient();
         HttpPost post = new HttpPost(
                         "http://ec2-34-207-60-3.compute-1.amazonaws.com:80/db_getscore.php");

         try {

                 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                 nameValuePairs.add(new BasicNameValuePair("levelID", level));
                 
                 

                 post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                 HttpResponse response = client.execute(post);
                 BufferedReader rd = new BufferedReader(new InputStreamReader(
                                 response.getEntity().getContent()));

                 String line = "";
                 while ((line = rd.readLine()) != null) {
                         System.out.println(line);
                         if (line.startsWith("Auth=")) {
                                 String key = line.substring(5);
                                 // do something with the key
                         }

                 }
         } catch (IOException e) {
                 e.printStackTrace();
         }
 }
}
