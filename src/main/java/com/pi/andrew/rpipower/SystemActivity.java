package com.pi.andrew.rpipower;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SystemActivity extends AppCompatActivity {
    ProgressBar progress_cpu,progress_mem,progress_swap,progress_disk;
    TextView text_cpu,text_mem,text_swap,text_disk;
    TextView text_disk_read,text_disk_write,text_net_send,text_net_recv;
    Handler h=new Handler();
    final SQLBase sqlbase=new SQLBase(this);
    final SystemMonitor monitor = new SystemMonitor();
    protected void clear(){
        SystemMonitor.result = "0";
        SystemMonitor.result2 = "0";
        SystemMonitor.result3 = "0";
        SystemMonitor.result4 = "0";
        SystemMonitor.result_int = 0;
        SystemMonitor.result_int2 = 0;
        SystemMonitor.result_int3 = 0;
        SystemMonitor.result_int4 = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clear();
        h.removeCallbacks(refresh);
    }
    Runnable refresh=new Runnable() {
        @Override
        public void run() {
            try {
                SQLiteDatabase sql = sqlbase.getReadableDatabase();
                int cpu, mem, swap, disk;
                String disk_read, disk_write, net_sent, net_recv;
                cpu = monitor.cpu_usage(sql);
                mem = monitor.memory_usage(sql);
                swap = monitor.swap_usage(sql);
                disk = monitor.disk_usage(sql);
                disk_read = monitor.disk_read(sql);
                disk_write = monitor.disk_write(sql);
                net_sent = monitor.network_sent(sql);
                net_recv = monitor.network_recv(sql);
                progress_cpu.setProgress(cpu);
                progress_mem.setProgress(mem);
                progress_swap.setProgress(swap);
                progress_disk.setProgress(disk);
                text_cpu.setText(cpu + "%");
                text_mem.setText(mem + "%");
                text_swap.setText(swap + "%");
                text_disk.setText(disk + "%");
                text_disk_read.setText("Total Read:" + disk_read);
                text_disk_write.setText("Total Write:" + disk_write);
                text_net_send.setText("Total Send:" + net_sent);
                text_net_recv.setText("Total Recv:" + net_recv);
                } catch (Exception e) { }
                h.postDelayed(this, 1000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        clear();
        progress_cpu = (ProgressBar)findViewById(R.id.progressBar_cpu);
        progress_mem = (ProgressBar)findViewById(R.id.progressBar_mem);
        progress_swap = (ProgressBar)findViewById(R.id.progressBar_swap);
        progress_disk = (ProgressBar)findViewById(R.id.progressBar_disk);
        text_cpu = (TextView)findViewById(R.id.textView_cpu_pro);
        text_mem = (TextView)findViewById(R.id.textView_mem_pro);
        text_swap = (TextView)findViewById(R.id.textView_swap_pro);
        text_disk = (TextView)findViewById(R.id.textView_disk_pro);
        text_disk_read = (TextView)findViewById(R.id.textView_disk_read);
        text_disk_write = (TextView)findViewById(R.id.textView_disk_write);
        text_net_send = (TextView)findViewById(R.id.textView_network_send);
        text_net_recv = (TextView)findViewById(R.id.textView_network_recv);
        h.postDelayed(refresh,1000);
    }
}
