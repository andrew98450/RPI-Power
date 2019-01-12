package com.pi.andrew.rpipower;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLBase sql=new SQLBase(this);
    Handler h=new Handler();
    EditText editText;
    WifiManager wifiManager;
    List<ScanResult> scanResults;
    SystemMonitor monitor = new SystemMonitor();
    TextView textView;
    ListView listView;
    Button bt;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_manager:
                Intent manager_intent = new Intent(this,SystemActivity.class);
                startActivity(manager_intent);
                break;
            case R.id.item_login:
                Intent login_intent = new Intent(this,LoginActivity.class);
                startActivity(login_intent);
                break;
            case R.id.item_poweroff:
                AlertDialog.Builder is_poweroff=new AlertDialog.Builder(this);
                is_poweroff.setTitle("RPI Power");
                is_poweroff.setMessage("Poweroff?");
                is_poweroff.setPositiveButton("No",null);
                is_poweroff.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase sqLiteDatabase_poweroff = sql.getReadableDatabase();
                        monitor.poweroff(sqLiteDatabase_poweroff);
                    }
                });
                is_poweroff.show();
                break;
            case R.id.item_reboot:
                AlertDialog.Builder is_reboot=new AlertDialog.Builder(this);
                is_reboot.setTitle("RPI Power");
                is_reboot.setMessage("Reboot?");
                is_reboot.setPositiveButton("No", null);
                is_reboot.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase sqLiteDatabase_reboot = sql.getReadableDatabase();
                        monitor.reboot(sqLiteDatabase_reboot);
                    }
                });
                is_reboot.show();
                break;
            case R.id.item_disconnect:
                AlertDialog.Builder is_disconnect=new AlertDialog.Builder(this);
                is_disconnect.setTitle("RPI Power");
                is_disconnect.setMessage("Disconnect?");
                is_disconnect.setPositiveButton("No", null);
                is_disconnect.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       wifiManager.disconnect();
                    }
                });
                is_disconnect.show();
                break;
            case R.id.item_exit:
                AlertDialog.Builder is_exit=new AlertDialog.Builder(this);
                is_exit.setTitle("RPI Power");
                is_exit.setMessage("Exit?");
                is_exit.setPositiveButton("No", null);
                is_exit.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                is_exit.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView_connect);
        listView = (ListView)findViewById(R.id.listview);
        bt = (Button)findViewById(R.id.button_refresh);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        wifiManager=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(R.layout.activity_main);
                builder.setTitle("Enter Password");
                builder.setMessage("SSID:" + parent.getItemAtPosition(position).toString());
                editText=new EditText(MainActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                editText.setLayoutParams(lp);
                builder.setView(editText);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WifiConnect wifiConnect=new WifiConnect(MainActivity.this);
                        wifiConnect.openWifi();
                        wifiConnect.addNetwork(wifiConnect.CreateWifiInfo(parent.getItemAtPosition(position).toString(),editText.getText().toString(),3));
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                } else {
                    adapter.clear();
                    scanResults = wifiManager.getScanResults();
                    for(int i=0;i<scanResults.size();i++){
                        if(scanResults.get(i).capabilities.contains("WPA") || scanResults.get(i).capabilities.contains("WPA2"))
                            adapter.add(scanResults.get(i).SSID);
                    }
                    listView.setAdapter(adapter);
                }
            }
        });
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWifi.isConnected()) {
                    textView.setText("Connect Status:Wifi Device Connected");
                }else{
                    textView.setText("Connect Status: Wifi Device Disconnected");
                }
                h.postDelayed(this,1000);
            }
        },1000);
    }
}
