package tju.noil.wifi_ips;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by noil on 2016/5/8.
 */
public class PreWifiInfoList {
    public List<PreWifiInfo> list;
    public int max_pre;
    public PreWifiInfoList(){
        list=new ArrayList<PreWifiInfo>(3);
        max_pre=0;
    }
    public void defaultInit(){
        PreWifiInfo A=new PreWifiInfo(5.6,3.2,"70:ba:ef:cb:c2:01");
        PreWifiInfo B=new PreWifiInfo(20.6,3.2,"70:ba:ef:cb:7d:81");
        PreWifiInfo C=new PreWifiInfo(20.6,10.8,"70:ba:ef:cb:da:80");
        PreWifiInfo D=new PreWifiInfo(7.5,-1.7,"70:ba:ef:cb:c2:01");
        PreWifiInfo E=new PreWifiInfo(16.2,-1.0,"70:ba:ef:cb:c2:11");
        list.add(A);
        list.add(B);
        list.add(C);
        list.add(D);
        list.add(E);
        max_pre=list.size();
    }
    public void setIRSSI(int i,int rssi){
        list.get(i).RSSI = rssi;
    }
}

