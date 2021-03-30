package com.example.yflightinvapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String mobile_mac_address;
    TextView macaddress;
    WebView webView;
    Connection connect;
    String ConnectionResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView=findViewById(R.id.webView);
        mobile_mac_address = getMacAddress();

//        String mac=GetTextFromSql(mobile_mac_address);
//        String staticMac="E45D7591180F";
//        if(mac.equals(mobile_mac_address)){
////            Intent viewIntent=new Intent("android.intent.action.VIEW",
////                    Uri.parse("http://www.stackoverflow.com/"));
////            startActivity(viewIntent);
//            webView.setWebViewClient(new WebViewClient());
//            webView.getSettings().setJavaScriptEnabled(true);
//            webView.loadUrl("https://asr.yetiairlines.com/");
//        }
//        else{
//            //Log.d("MyMacIS",mobile_mac_address);
//            //macaddress.setText("You are not authorized");
//        }
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://192.168.10.78:100/FI/Index/"+mobile_mac_address);
    }

    public String GetTextFromSql(String mac){
        String result="";
        try{

            ConnectionHelper connectionHelper=new ConnectionHelper();
            connect =connectionHelper.connectionClass();
            if(connect!=null){
                String query="Select Mac_Address from Mac_Address where Mac_Address='"+mac+"'";
                Statement st=connect.createStatement();
                ResultSet rs=st.executeQuery(query);
                while(rs.next()){
                   result =rs.getString(1);

                }
            }
        }catch (Exception ex){
            ex.getMessage();
        }
        return result;
    }
    public String getMacAddress(){
        try{
            List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());

            String stringMac = "";

            for(NetworkInterface networkInterface : networkInterfaceList)
            {
                if(networkInterface.getName().equalsIgnoreCase("wlan0"))
                {
                    for(int i = 0 ;i <networkInterface.getHardwareAddress().length; i++){
                        String stringMacByte = Integer.toHexString(networkInterface.getHardwareAddress()[i]& 0xFF);

                        if(stringMacByte.length() == 1)
                        {
                            stringMacByte = "0" +stringMacByte;
                        }

                        stringMac = stringMac + stringMacByte.toUpperCase();
                    }
                    break;
                }

            }
            return stringMac;
        }catch (SocketException e)
        {
            e.printStackTrace();
        }

        return  "0";
    }

}