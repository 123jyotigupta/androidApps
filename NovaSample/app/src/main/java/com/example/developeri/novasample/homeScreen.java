package com.example.developeri.novasample;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;


public class homeScreen extends ActionBarActivity {
    String[] options = {"View Cards" ,"Profile","Logout"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        final ListView listView = (ListView) findViewById(R.id.optionsView);
        ImageView menuIamge = (ImageView) findViewById(R.id.menuImage);
        menuIamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        addItemsOnProjectListSpinner();
        addItemsOnActivitiesListSpinner();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,options);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               int itemPosition = position;
                Log.i("in","" + itemPosition);

                //getting value in a ro
               String itemvalue = (String)listView.getItemAtPosition(position);
                Log.i("item value","" + itemvalue);

                if(itemPosition == 0){

//                    Intent addCard = new Intent(homeScreen.this,addCardScreen.class);
//                    startActivity(addCard);
                }
                if(itemPosition == 2){
                    finish();
//                    Intent intent = new Intent(getApplicationContext(), mainScreen.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
                }
            }
        });




    }

    public void addItemsOnProjectListSpinner() {
        Spinner projectDropdown = (Spinner)findViewById(R.id.projectList);
        String[] projectItems = new String[]{"Select Project","Wynne Systems", "Hoaah", "proscope","Bookshelf"};
        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,projectItems);
        projectDropdown.setAdapter(adapterList);
    }

    public void addItemsOnActivitiesListSpinner() {
        Spinner activityDropdown = (Spinner)findViewById(R.id.activityList);
        String[] activityItems = new String[]{"Select Activity","Coding", "Bug fixes", "Architecture","Designing","PTO", "National Holiday","Estimate"};
        ArrayAdapter<String> adapterActivityList = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,activityItems);
        activityDropdown.setAdapter(adapterActivityList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
