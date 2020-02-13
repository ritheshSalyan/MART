package com.rithesh.chalo.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rithesh.chalo.Bus;
import com.rithesh.chalo.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class fromAdapter extends BaseAdapter {

    List<Bus> list;
    Context con;
    LayoutInflater inflter;
    String source,dest;

    public fromAdapter(Context con , List<Bus>  list1,String s,String d){
        this.con = con;
        this.list = list1;
        inflter = (LayoutInflater.from(con));
        source = s;
        dest = d;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = inflter.inflate(R.layout.bus_list, null);
        TextView from = view.findViewById(R.id.from_list);
        from.setText(list.get(position).from);

        TextView to = view.findViewById(R.id.to_list);
        to.setText(list.get(position).to);

        TextView time = view.findViewById(R.id.time_list);
        float dt = Float.parseFloat(list.get(position).distance);
        String[] via = list.get(position).via.split(";");
        float tr = (float) (dt/0.833333) / Integer.valueOf(via.length); //approximate average time assumed is 50km/hr i,e 0.8333 km/min
        String[] stime = list.get(position).start.split(":");
        int index =0;
        List<String> viaList = Arrays.asList(list.get(position).via.split(";"));
        if(source.compareTo(list.get(position).from) == 0 ) {
            index = 1;
        }
        else{
            index =viaList.indexOf(source);
            Log.d("Time  ",index+" ");
            index++;
        }

        stime[0] = String.valueOf((Integer.valueOf(stime[0])+(int)((tr/60)*index))%24);
        stime[1] = String.valueOf((Integer.valueOf(stime[1])+(int)((tr%60)*index))%60);
        time.setText(stime[0]+":"+stime[1]);
        Log.d("Time  ",source+" "+dest+" "+stime[0]+" "+stime[1]+" "+list.get(position).start+" "+tr+" "+index+" "+(((tr%60)*index))%60);


        TextView in = view.findViewById(R.id.inter);
        in.setText(list.get(position).inter);

        String ctop = current_stop(position);
        TextView cs = view.findViewById(R.id.current_stop);
        cs.setText(ctop);

        TextView cos = view.findViewById(R.id.cost);

        cos.setText("cost "+(index*10));



        return view;
    }

    String current_stop(int i){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int current_time = hour+minute;
        String[] via = list.get(i).via.split(";");
        String[] stime = list.get(i).start.split(":");
        String[] rtime = list.get(i).reach.split(":");
        int total_time = (Integer.valueOf(rtime[0])*60 -   Integer.valueOf(stime[0])*60)+(Integer.valueOf(rtime[1])- Integer.valueOf(stime[1]));
        float avg_time = total_time/5;//via.length;
        Log.d("Current stop",list.get(i).from+" "+list.get(i).to+" "+stime[0]+":"+stime[1]+"   "+total_time+"  "+avg_time+" "+current_time+" "+via.length+"  "+via[0]);
        for(int s = 0; s< via.length; s++){

            if( (float)current_time > avg_time*(s+1)){
                Log.d("Current stop",via[s]);
                return via[s];
            }

        }

        return null;
    }
}
