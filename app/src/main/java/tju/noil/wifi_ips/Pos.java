package tju.noil.wifi_ips;

import java.text.DecimalFormat;

/**
 * Created by noil on 2016/5/8.
 */
public class Pos {
    public double x;
    public double y;
    public Pos (){
        this.x=0;
        this.y=0;
    }
    public Pos(double x,double y){
        this.x=x;
        this.y=y;
    }
    public void print(){
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("x:"+df.format(x)+" y:"+df.format(y));
    }
    public double distance(Pos b){
        double d=Math.sqrt((b.x-this.x)*(b.x-this.x)+(b.y-this.y)*(b.y-this.y));
        return d;
    }
//    public Pos online (double d,Pos b){
//        Pos r=new Pos();
//        double l=this.distance(b);
//
////		r.x=Math.min(this.x,b.x)+Math.abs(this.x-b.x)*d/l;
////		r.y=Math.min(this.y,b.y)+Math.abs(this.y-b.y)*d/l;
//        r.x=this.x+d*(b.x-this.x)/l;
//        r.y=this.y+d*(b.y-this.y)/l;
//        return r;
//    }
}

