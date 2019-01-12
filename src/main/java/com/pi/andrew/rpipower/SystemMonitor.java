package com.pi.andrew.rpipower;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
public class SystemMonitor {
    static String result="0",result2="0",result3="0",result4="0";
    static int result_int = 0,result_int2=0,result_int3=0,result_int4=0;

    public void poweroff(SQLiteDatabase sql){
        AsyncHttpClient client = new AsyncHttpClient();
        Cursor cur = sql.rawQuery("SELECT * FROM logins",null);
        cur.moveToFirst();
        String username = cur.getString(0);
        String password = cur.getString(1);
        client.setBasicAuth(username,password);
        client.post("http://10.0.0.1:8000/macros/poweroff", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }
    public void reboot(SQLiteDatabase sql){
        AsyncHttpClient client = new AsyncHttpClient();
        Cursor cur = sql.rawQuery("SELECT * FROM logins",null);
        cur.moveToFirst();
        String username = cur.getString(0);
        String password = cur.getString(1);
        client.setBasicAuth(username,password);
        client.post("http://10.0.0.1:8000/macros/reboot", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }
    public int cpu_usage(SQLiteDatabase sql){
        AsyncHttpClient client = new AsyncHttpClient();
        Cursor cur = sql.rawQuery("SELECT * FROM logins",null);
        cur.moveToFirst();
        String username = cur.getString(0);
        String password = cur.getString(1);
        client.setBasicAuth(username,password);
        client.post("http://10.0.0.1:8000/macros/cpu_usage", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String data = new String(bytes,0,bytes.length);
                if(data.length() == 0)
                    result_int = 0;
                else
                    result_int = Integer.parseInt(data);
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return result_int;
    }
    public int memory_usage(SQLiteDatabase sql){
        AsyncHttpClient client = new AsyncHttpClient();
        Cursor cur = sql.rawQuery("SELECT * FROM logins",null);
        cur.moveToFirst();
        String username = cur.getString(0);
        String password = cur.getString(1);
        client.setBasicAuth(username,password);
        client.post("http://10.0.0.1:8000/macros/memory_usage", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String data = new String(bytes,0,bytes.length);
                if(data.length() == 0)
                    result_int2 = 0;
                else
                    result_int2 = Integer.parseInt(data);
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return result_int2;
    }
    public int swap_usage(SQLiteDatabase sql){
        AsyncHttpClient client = new AsyncHttpClient();
        Cursor cur = sql.rawQuery("SELECT * FROM logins",null);
        cur.moveToFirst();
        String username = cur.getString(0);
        String password = cur.getString(1);
        client.setBasicAuth(username,password);
        client.post("http://10.0.0.1:8000/macros/swap_usage", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String data = new String(bytes,0,bytes.length);
                if(data.length() == 0)
                    result_int3 = 0;
                else
                    result_int3 = Integer.parseInt(data);
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return result_int3;
    }
    public int disk_usage(SQLiteDatabase sql){
        AsyncHttpClient client = new AsyncHttpClient();
        Cursor cur = sql.rawQuery("SELECT * FROM logins",null);
        cur.moveToFirst();
        String username = cur.getString(0);
        String password = cur.getString(1);
        client.setBasicAuth(username,password);
        client.post("http://10.0.0.1:8000/macros/disk_usages", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String data = new String(bytes,0,bytes.length);
                if(data.length() == 0)
                    result_int4 = 0;
                else
                    result_int4 = Integer.parseInt(data);
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return result_int4;
    }
    public String disk_read(SQLiteDatabase sql){
        AsyncHttpClient client = new AsyncHttpClient();
        Cursor cur = sql.rawQuery("SELECT * FROM logins",null);
        cur.moveToFirst();
        String username = cur.getString(0);
        String password = cur.getString(1);
        client.setBasicAuth(username,password);
        client.post("http://10.0.0.1:8000/macros/disk_read", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String data = new String(bytes,0,bytes.length);
                result = data;
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return result;
    }
    public String disk_write(SQLiteDatabase sql){
        AsyncHttpClient client = new AsyncHttpClient();
        Cursor cur = sql.rawQuery("SELECT * FROM logins",null);
        cur.moveToFirst();
        String username = cur.getString(0);
        String password = cur.getString(1);
        client.setBasicAuth(username,password);
        client.post("http://10.0.0.1:8000/macros/disk_write", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String data = new String(bytes,0,bytes.length);
                result2 = data;
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return result2;
    }
    public String network_sent(SQLiteDatabase sql){
        AsyncHttpClient client = new AsyncHttpClient();
        Cursor cur = sql.rawQuery("SELECT * FROM logins",null);
        cur.moveToFirst();
        String username = cur.getString(0);
        String password = cur.getString(1);
        client.setBasicAuth(username,password);
        client.post("http://10.0.0.1:8000/macros/network_sent", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String data = new String(bytes,0,bytes.length);
                result3 = data;
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return result3;
    }
    public String network_recv(SQLiteDatabase sql){
        AsyncHttpClient client = new AsyncHttpClient();
        Cursor cur = sql.rawQuery("SELECT * FROM logins",null);
        cur.moveToFirst();
        String username = cur.getString(0);
        String password = cur.getString(1);
        client.setBasicAuth(username,password);
        client.post("http://10.0.0.1:8000/macros/network_recv", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String data = new String(bytes,0,bytes.length);
                result4 = data;

            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return result4;
    }
}
