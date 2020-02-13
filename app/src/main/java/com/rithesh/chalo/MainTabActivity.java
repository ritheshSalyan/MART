package com.rithesh.chalo;



import android.support.design.widget.TabLayout;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
import android.widget.TabHost;


public class MainTabActivity extends AppCompatActivity {

    // TODO: 8/7/2018 Variables Declaration section....
    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_activity);

        // TODO: 8/10/2018 Tool bar....
//        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
//        toolbar.setTitle("Child Monitor");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        // TODO: 8/8/2018 Adding Fragment Layout in TabActivity...
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MainActivity(),"Set");
        adapter.addFragment(new MapsActivity(),"Location");
        adapter.addFragment(new BusView(),"List");


        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_phone);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_contacts);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_message);
//        tabLayout.getTabAt(3).setIcon(R.drawable.ic_map);
//        tabLayout.getTabAt(4).setIcon(R.drawable.ic_apps);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

        public void ch(int a){
            switchTabInActivity(a);
        }
    public void switchTabInActivity(int indexTabToSwitchTo){
        MainTabActivity parentActivity;
        parentActivity = (MainTabActivity) this.getParent();
        parentActivity.switchTab(indexTabToSwitchTo);
    }


    public  void switchTab(int tab){
        MainTabActivity ta = (MainTabActivity) this.getParent();
        TabHost th = ta.findViewById(android.R.id.tabhost);
        th.setCurrentTab(tab);
    }
}