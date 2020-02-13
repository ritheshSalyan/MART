package com.rithesh.chalo;

public class Bus implements Comparable< Bus>{
    public String from;
    public String to;
   public String via;
   public String start;
  public   String reach;
  public String distance;
    public String inter = "";


    Bus(String f,String t,String v,String s,String r,String d){
        from = f;
        to = t;
        via = v;
        start = s;
        reach = r;
        distance = d;

    }
    String getFrom(){
        return from;
    }

    String getVia(){

        return via;
    }

    @Override
    public int compareTo(Bus bus) {
       return this.start.compareTo(bus.start);
    }
}
