package com.rithesh.chalo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rithesh.chalo.Adapter.fromAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BusView extends Fragment {
    static String source ="",dest="",time="",intermid = "";
    private View v;
    public static Bus bus_selected;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.buslist, container, false);
        super.onCreate(savedInstanceState);

        source =MainActivity.source;//getActivity().getIntent().getStringExtra("From");
        dest = MainActivity.dest;//getActivity().getIntent().getStringExtra("To");
        time = MainActivity.time;//getActivity().getIntent().getStringExtra("Time");


        Log.d("From TO Time",source+" "+dest+" "+time);

        InputStream inputStream = getResources().openRawResource(R.raw.busdata);
        CSVFile csvFile = new CSVFile(inputStream);
        List<Bus> bus = csvFile.read();
        List<Bus> bus1 = new LinkedList<>(bus);
        List<Bus> l1 = new ArrayList<Bus>();
        HashSet<Bus> l = new HashSet<Bus>();
        if(source != "" && dest != "") {
            for (Bus b : bus1) {
                // Log.d("Comapring string",source+"="+b.from+"   "+dest+"="+b.to);
                List<String> viaList = Arrays.asList(b.via.split(";"));
                for (Bus c : bus1) {
                    String in = "";
                    if (c.via.contains(dest) && b.via.contains(source) && !b.via.contains(dest)) {
                        for (String inter : viaList) {
                            if (c.via.contains(inter)) {
                                in = inter;
                                break;
                            }
                        }
                        if (time.compareTo(b.start) < 0 && !in.equalsIgnoreCase("") && !in.equalsIgnoreCase(source)) {
                            if (!in.equalsIgnoreCase(dest)) {
                                intermid = b.to = in;
                                b.inter = intermid;
                                l.add(b);
                                Log.d("TIme = ", b.from + " = " + source + " =>" + in + "=> " + dest);
                            }
                        }
                    }
                }
            }
            Log.d("TIme = ", "before going to adapter size " + bus.size());
            for (Bus b : bus) {
                List<String> viaList = Arrays.asList(b.via.split(";"));
                if ((b.via.contains(source) && b.via.contains(dest)) && (viaList.indexOf(source) < viaList.indexOf(dest))) {
                    Log.d("Comapring string", source + "=" + dest + "=" + b.via);
                    Log.d("Comapring string", source + "=" + viaList.indexOf(source) + "\n" + dest + "=" + viaList.indexOf(dest));
                    if (time.compareTo(b.start) < 0) {
                        l1.add(b);
                        Log.d("TIme = ", b.start);
                    }
                }
            }

            final List<Bus> lList = new LinkedList<Bus>(l1);
            lList.addAll(l);
            Collections.sort(lList);
            Log.d("TIme = ", "going to adapter size " + l.size());
            // if(lList.size()!=0) {
            ListView listView = v.findViewById(R.id.listview);
            fromAdapter adapter = new fromAdapter(getContext(), lList, source, dest);
            listView.setAdapter(adapter);
            listView.setEmptyView(v.findViewById(R.id.emptyElement));


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    bus_selected = lList.get(position);
                    ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
                    viewPager.setCurrentItem(1);
                    restartActivity(getActivity());

                }
            });
        }
//        }
//        else{
//        }
        return v;
    }

    public static void restartActivity(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }
}
