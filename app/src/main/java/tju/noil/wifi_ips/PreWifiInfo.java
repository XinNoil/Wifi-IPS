package tju.noil.wifi_ips;

/**
 * Created by noil on 2016/5/8.
 */
public class PreWifiInfo {
    public Pos pos;
    public String BSSID;
    public double RSSI;
    public PreWifiInfo(double x,double y,String BSSID){
        pos=new Pos(x,y);
        this.BSSID=BSSID;
    }
}
