package tju.noil.wifi_ips;

import android.net.wifi.ScanResult;

/**
 * Created by noil on 2016/5/8.
 */
public class myScanResult {
    public ScanResult scanResult;
    public double distance;
    public double Pt=0;
    public myScanResult(ScanResult scanResult){
        this.scanResult=scanResult;
        if (scanResult.level>-58.5){
            double mi=(-scanResult.level-40.2+Pt)/20;
            distance=Math.pow(10,mi);
        }
        else{
            double mi=(-scanResult.level-28.7+Pt)/33;
            distance=Math.pow(10,mi);
        }
    }
}
