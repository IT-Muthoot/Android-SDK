package com.muthootfinance.retrievedetails;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.muthootfinance.retreivesdk.BuildConfig;
import com.muthootfinance.retreivesdk.CallLogModel;
import com.muthootfinance.retreivesdk.MainActivity;
import com.muthootfinance.retreivesdk.SMSModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GetDeviceDetails extends Activity {

    List<String> list = new ArrayList<>();
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd", Locale.US);
    SimpleDateFormat simpleTime = new SimpleDateFormat("hh:mm:ss", Locale.US);
    String time, date;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private String android_id;
    String currentDate, currentTime;
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.READ_CALL_LOG) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.READ_SMS) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.READ_PHONE_STATE)  + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.INTERNET) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.ACCESS_NETWORK_STATE) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (this, Manifest.permission.READ_CONTACTS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (this, Manifest.permission.READ_CALL_LOG) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (this, Manifest.permission.READ_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (this, Manifest.permission.READ_PHONE_STATE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (this, Manifest.permission.INTERNET) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (this, Manifest.permission.ACCESS_NETWORK_STATE)  ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{Manifest.permission
                                    .READ_CONTACTS, Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_MULTIPLE_REQUEST);
//                    getPhoneContacts();
//            getCallLogs();
//            getAllSms();
//            getInstalledApps();
//            getDeviceDetails();
                }
//                Snackbar.make(getActivity().findViewById(android.R.id.content),
//                        "Please Grant Permissions to upload profile photo",
//                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                requestPermissions(
//                                        new String[]{Manifest.permission
//                                                .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
//                                        PERMISSIONS_MULTIPLE_REQUEST);
//                            }
//                        }).show();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{Manifest.permission
                                    .READ_CONTACTS, Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }
            }
        } else {
//            getPhoneContacts();
//            getCallLogs();
            getAllSms();
//            getInstalledApps();
//            getDeviceDetails();
        }
    }

    @SuppressLint({"Range", "HardwareIds"})
    public void getPhoneContacts() {
        int count = 0;
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        Log.i("ContactDemo", "Total # of Contacts" + Integer.toString(cursor.getCount()));
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Log.d("ContactDemo", "----Fetch Contact");
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("ContactDemo", "Name: " + name);
                        list.add(name + " - " + phoneNo);
                        Log.i("ContactDemo", "Phone Number: " + phoneNo);
//                            callContactPushApi(name, phone+No, currentDate, currentTime, android_id, "Android SDK", "Android SDK");
//                            generateFile(count++, name, phoneNo, currentDate, currentTime, android_id, "Android SDK", "Android SDK");
                    }
                    callContactPushApi(list.toString(), "Phone", currentDate, currentTime, android_id, "Android SDK", "Android SDK");
                    pCur.close();
                }

            }
        }
    }

    void generateFile(int count, String name, String number, String date, String time, String deviceId, String uid, String lusr) {
        Long tsLong = System.currentTimeMillis() / 1000;
        try {
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "TextFile");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, deviceId + "_" + tsLong.toString() + "_" + count + ".txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(name + "\n");
            writer.append(number + "\n");
            writer.append(date + "\n");
            writer.append(time + "\n");
            writer.append(deviceId + "\n");
            writer.append(uid + "\n");
            writer.append(lusr + "\n");
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void callContactPushApi(String name, String number, String date, String time, String deviceId, String uid, String lusr) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://10.85.207.85:2024/api/SaveContactDetails";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", 1);
            jsonBody.put("Name", name);
            jsonBody.put("Number", number);
            jsonBody.put("Date", date);
            jsonBody.put("Time", time);
            jsonBody.put("Device_Id", deviceId);
            jsonBody.put("Uid", uid);
            jsonBody.put("SdkVersion", BuildConfig.VERSION_NAME);
            jsonBody.put("LUSR", lusr);
            final String requestBody = jsonBody.toString();
            Log.d("RequestBody", requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY-Response Contact", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY-Error", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        Log.i("VOLLEY-Res", responseString);
                        Log.i("VOLLEY-ResDATA", response.data.toString());
                        // can get more details such as response.headers
                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    return super.parseNetworkResponse(response);
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HardwareIds")
    public void getCallLogs() {
        List<String> lstCallLog = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int cache_name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String name = managedCursor.getString(cache_name);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            Log.d("CallDate", callDate);
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            CallLogModel callLogModel = new CallLogModel(phNumber, name, dir, callDayTime.toString(),callDuration);
            String jsonString = new Gson().toJson(callLogModel);
            lstCallLog.add(jsonString);
//                sb.append(phNumber + "---" + name + "---" + dir + "---" + callDayTime + "---" + callDuration);
//                callCallLogPushApi(1, phNumber, name, callDuration, dir, callDate, android_id, "Android SDK", "Android SDK");
        }
        managedCursor.close();
        callCallLogPushApi(1, "phNumber", lstCallLog.toString(), "callDuration", "dir", "callDate", android_id, "Android SDK", "Android SDK");
    }

    void callCallLogPushApi(int id, String number, String name, String duration, String callType, String timestamp, String deviceId, String uid, String lusr) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://10.85.207.85:2024/api/SaveCallHistory";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("ID", id);
            jsonBody.put("NUMBER", number);
            jsonBody.put("NAME", name);
            jsonBody.put("DURATION", duration);
            jsonBody.put("CallType", callType);
            jsonBody.put("TIMESTAMP", timestamp);
            jsonBody.put("DEVICE_ID", deviceId);
            jsonBody.put("UID", uid);
            jsonBody.put("SDKVERSION", BuildConfig.VERSION_NAME);
            jsonBody.put("LUSR", lusr);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOY-RespCall Log", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY-Error", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        Log.i("VOLLEY-Res", responseString);
                        Log.i("VOLLEY-ResDATA", response.data.toString());
                        // can get more details such as response.headers
                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    return super.parseNetworkResponse(response);
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HardwareIds")
    public void getAllSms() {
        List<String> lstSms = new ArrayList<>();
        String dateString = formatter.format(new Date(Long.parseLong(time)));
        String timeString = formatterTime.format(new Date(Long.parseLong(time)));
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = this.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        this.startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                String address = c.getString(c
                        .getColumnIndexOrThrow("address"));
                String msg = c.getString(c.getColumnIndexOrThrow("body"));
                @SuppressLint("Range") String readState = c.getString(c.getColumnIndex("read"));
                String time = c.getString(c.getColumnIndexOrThrow("date"));
                //if readState = 1 then inbox else sent
//                    lstSms.add(address + msg + readState + time);
                SMSModel smsModel = new SMSModel(address, msg, readState, time);
                String jsonString = new Gson().toJson(smsModel);
                lstSms.add(jsonString);

//                    callMessagePushApi(1, address, msg, dateString, timeString, android_id, "Android SDK", "Android SDK");
//                    sendData(1, address, msg, dateString, timeString, android_id, "Android SDK", "Android SDK");
                c.moveToNext();
            }

        }

//        sendData(1,  "Send Data",lstSms.toString(), "dateString", "timeString", android_id, "Android SDK", "Android SDK");
        Log.d("SMS - ", lstSms.toString());
        callMessagePushApi(1, lstSms.toString(), "Call Method", dateString, timeString, android_id, "Android SDK", "Android SDK");
        c.close();

    }

    void generateFile(String body) {
        Long tsLong = System.currentTimeMillis() / 1000;
        try {
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "TextFile");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, tsLong.toString() +".txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(body);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void callMessagePushApi(int id, String smsFrom, String body, String date, String time, String deviceId, String uid, String lusr) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://10.85.207.85:2024/api/SaveSmsDetails";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", id);
            jsonBody.put("SmsFrom", smsFrom);
            jsonBody.put("SmsBody", body);
            jsonBody.put("SmsDate", date);
            jsonBody.put("Time", time);
            jsonBody.put("DeviceId", deviceId);
            jsonBody.put("UId", uid);
            jsonBody.put("SDKVERSION", BuildConfig.VERSION_NAME);
            jsonBody.put("LUSR", lusr);
            final String requestBody = jsonBody.toString();
//            generateFile(jsonBody.toString());
            Log.d("Request Body SMS", requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY-Response SMS", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY-Error", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        Log.i("VOLLEY-Res", responseString);
                        Log.i("VOLLEY-ResDATA", response.data.toString());
                        // can get more details such as response.headers
                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    return super.parseNetworkResponse(response);
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("HardwareIds")
    public void getInstalledApps(){
        List<String> appsList = new ArrayList<>();
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            Log.d(TAG, "Installed package :" + packageInfo.packageName);
            Log.d(TAG, "Installed App name :" + packageInfo.name);
            appsList.add(packageInfo.packageName);
//            callAppsPushApi(1, packageInfo.name, packageInfo.packageName, date, time, android_id, "Android SDK", "Android SDK");
            Log.d(TAG, "Inserted");
        }
        callAppsPushApi(1, appsList.toString(), "packageName", date, time, android_id, "Android SDK", "Android SDK");
    }

    void callAppsPushApi(int id, String appName, String packageName, String date, String time, String deviceId, String uid, String lusr) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://10.85.207.85:2024/api/SaveAppsDetails";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Id", id);
            jsonBody.put("AppName", appName);
            jsonBody.put("Package", packageName);
            jsonBody.put("Date", date);
            jsonBody.put("Time", time);
            jsonBody.put("DeviceId", deviceId);
            jsonBody.put("UId", uid);
            jsonBody.put("SDKVERSION", BuildConfig.VERSION_NAME);
            jsonBody.put("LUSR", lusr);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY-Response", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY-Error", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        Log.i("VOLLEY-Res", responseString);
                        Log.i("VOLLEY-ResDATA", response.data.toString());
                        // can get more details such as response.headers
                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    return super.parseNetworkResponse(response);
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HardwareIds")
    public void getDeviceDetails() {
        callDeviceDetailsPushApi(1, "Android", Build.BRAND, Build.MODEL, String.valueOf(Build.VERSION.SDK_INT), currentDate, currentTime, android_id, "Android SDK", "Android SDK");
    }

    void callDeviceDetailsPushApi(int id, String osType, String device, String model, String osVersion, String date, String time, String deviceId, String uid, String lusr) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://10.85.207.85:2024/api/SaveDeviceDetails";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("ID", id);
            jsonBody.put("OS_TYPE", osType);
            jsonBody.put("DEVICE", device);
            jsonBody.put("MODEL", model);
            jsonBody.put("OS_VERSION", osVersion);
            jsonBody.put("DATE", date);
            jsonBody.put("TIME", time);
            jsonBody.put("DEVICE_ID", deviceId);
            jsonBody.put("UID", uid);
            jsonBody.put("SDKVERSION", BuildConfig.VERSION_NAME);
            jsonBody.put("LUSR", lusr);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY-Response", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY-Error", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        Log.i("VOLLEY-Res", responseString);
                        Log.i("VOLLEY-ResDATA", response.data.toString());
                        // can get more details such as response.headers
                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    return super.parseNetworkResponse(response);
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
