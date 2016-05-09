package tju.noil.wifi_ips;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private TextView indexView;
    private TextView wifiListView;
    private TextView targetView;
    private Button button;
    private Button save_button;
    private StringBuilder stra;
    private StringBuilder strb;
    private wifiAdmin wA;
    private File file;
    private FileOutputStream fos;
    private int dataIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indexView= (TextView)findViewById(R.id.textView);
        targetView = (TextView)findViewById(R.id.textView3);
        wifiListView = (TextView)findViewById(R.id.textView2);
        button=(Button)findViewById(R.id.button);
        save_button=(Button)findViewById(R.id.save_button);
        wA=new wifiAdmin(this);
        startWifiMonitor(wA);
        ButtonListener buttonListener=new ButtonListener();
        button.setOnClickListener(buttonListener);
        ButtonListener saveButtonListener=new ButtonListener();
        save_button.setOnClickListener(saveButtonListener);
        File appPath = this.getExternalFilesDir(null);
        file = new File(appPath, "data.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Toast.makeText(this, file.toString(), Toast.LENGTH_LONG).show();
    }

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.button){
                reWifiMonitor(wA);
                dataIndex=0;
                String str="Save Index:"+Integer.toString(dataIndex);
                indexView.setText(str);
            }
            else if(v.getId()==R.id.save_button){
                reWifiMonitor(wA);
                saveData();
            }
        }
    }

    public void startWifiMonitor(wifiAdmin wA){
        wA.StartScan();
        stra= wA.LookUptarget();
        targetView.setText(stra.toString());
        strb=wA.LookUpScan();
        wifiListView.setText(strb.toString());
    }
    public void reWifiMonitor(wifiAdmin wA){
        wA.ReScan();
        stra= wA.LookUptarget();
        targetView.setText(stra.toString());
        strb=wA.LookUpScan();
        wifiListView.setText(strb.toString());
    }
    public void saveData(){
        String content = Integer.toString(dataIndex)+" "+stra.toString();
        try {
            fos = new FileOutputStream(file,true);
            byte [] bytes = content.getBytes();
            fos.write(bytes);
            fos.close();

            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataIndex++;
        String str="Save Index:"+Integer.toString(dataIndex);
        indexView.setText(str);
    }
}
