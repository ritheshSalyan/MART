package com.rithesh.chalo;

import android.app.Activity;
import android.app.Fragment;
import android.app.TabActivity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;



public class MainActivity extends Fragment implements AdapterView.OnItemSelectedListener {

    TextView TimeView,FromView;
    Spinner spinner;
    private Spinner spinner1;

    static String source = " ";
  static String dest = " ";
    private TextView TimeSet;
    Button check;
     static String time= "6:00";
    private View v;
    private TextView ToView;
    boolean flagf =true,flagt = true;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.activity_main, container, false);

        TimeSet = v.findViewById(R.id.time);
        TimeView = v.findViewById(R.id.time);
        FromView = v.findViewById(R.id.from);
        ToView = v.findViewById(R.id.to);
        spinner  = v.findViewById(R.id.spinner);
        spinner1  = (Spinner)v.findViewById(R.id.spinner2);
        check = v.findViewById(R.id.check);



        InputStream inputStream = getResources().openRawResource(R.raw.busdata);
        CSVFile csvFile = new CSVFile(inputStream);
        List<Bus> bus = csvFile.read();
        List<String> l = new ArrayList<String>();
        ArraySet<String> from;

            from = new ArraySet<>();


        Log.d("List bus ",bus.get(1).from);
       // FromView.setText(bus.get(1).getFrom());
        for(int i=0;i<bus.size();i++){
            for(String s : bus.get(i).getVia().split(";")) {
                from.add(s);
            }
        }

        for(int i = 0; i<bus.size();i++){
            from.add(bus.get(i).getFrom());
        }

        for(String s : from){
            l.add(s);

        }
        Collections.sort(l);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,l);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,l);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);




        TimeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                       time =  selectedHour + ":" + selectedMinute;

                        TimeView.setText(time);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
                viewPager.setCurrentItem(2);
            }
        });

        return v;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        switch (parent.getId()){
            case R.id.spinner:
                source = item;
                Toast.makeText(parent.getContext(), "Selected: " + source, Toast.LENGTH_LONG).show();
                if(flagf) {
                    flagf = false;
                }
                else {
                    FromView.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.spinner2:
                dest = item;
                Toast.makeText(parent.getContext(), "Selected: " + dest, Toast.LENGTH_LONG).show();
                if(flagt) {
                    flagt = false;
                }
                else {
                    ToView.setVisibility(View.INVISIBLE);
                }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void switchTabInActivity(int indexTabToSwitchTo){
        MainTabActivity parentActivity;
     //   parentActivity = (MainTabActivity) this.getParent();
       // parentActivity.switchTab(indexTabToSwitchTo);
    }

}

