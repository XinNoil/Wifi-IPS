package tju.noil.wifi_ips;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noil on 2016/5/8.
 */
public class PreWifiInfoList {
    public List<PreWifiInfo> list;
    public PreWifiInfoList(){
        PreWifiInfo A=new PreWifiInfo(5.6,3.2,"70:ba:ef:cb:c2:11");
        PreWifiInfo B=new PreWifiInfo(20.6,3.2,"70:ba:ef:cb:7d:81");
        PreWifiInfo C=new PreWifiInfo(20.6,10.8,"70:ba:ef:cb:da:80");
        list=new ArrayList<PreWifiInfo>(3);
        list.add(A);
        list.add(B);
        list.add(C);
        list.get(0).RSSI=0;
        list.get(1).RSSI=0;
        list.get(2).RSSI=0;
    }
    public void setIRSSI(int i,int rssi){
        list.get(i).RSSI = rssi;
    }
}
