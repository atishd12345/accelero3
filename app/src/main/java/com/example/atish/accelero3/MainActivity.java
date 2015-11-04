package com.example.atish.accelero3;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RoomListFragment.Callbacks {


    private static final int ASK_ACTIVATION = 1;
    private static final int ASK_CONNECTION = 2;
    private BluetoothAdapter btadapter = null;
    private static String MAC = null;

    private boolean mTwoPane;

    //   Used to store the last screen title. For use in {@link restoreActionBar()}.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // start the Main fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RoomListFragment()).commit();

        }

        //INITIALISE BLUETOOTH
        BluetoothInit();

        //initialise floating action button
        FloatingActionButton();
        //set up navigation Drawer
        NavigationDrawer();


        // masterflow

        if (findViewById(R.id.room_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((RoomListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.container))
                    .setActivateOnItemClick(true);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ASK_ACTIVATION:
                if(resultCode== Activity.RESULT_OK)
                    Toast.makeText(MainActivity.this, "BT active", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(MainActivity.this, "BT inactive", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }


    }

    public void BluetoothInit(){
        btadapter = BluetoothAdapter.getDefaultAdapter();

        if(btadapter==null){
            Toast.makeText(MainActivity.this, "oops! NO BT", Toast.LENGTH_SHORT).show();
        }

        if(btadapter.isEnabled()){
            Intent active = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(active, ASK_ACTIVATION);
        }


    }
    //sets up Navigation drawer
    public void NavigationDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    //sets up floating action button
     public void FloatingActionButton(){
         FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Snackbar.make(view, "Replace with quick BT settings", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();
             }
         });
     }

    /**
     * Callback method from {@link RoomListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(RoomDetailFragment.ARG_ITEM_ID, id);
            RoomDetailFragment fragment = new RoomDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.room_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, RoomDetailActivity.class);
            detailIntent.putExtra(RoomDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

            //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
         //   Toast.makeText(this, "Settings Pressed", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // methods for implementing navdrawer
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        FragmentManager fragmentManager = getFragmentManager();
//        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        RoomListFragment RoomListFrag = new RoomListFragment();
        MainFragment MainFrag = new MainFragment();
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the Home action
            String tag= "tag";
            // getFragmentManager().beginTransaction().replace(R.id.container, new RoomListFragment(), "stuff").commit();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RoomListFrag)
                    .commit();
//            transaction.beginTransaction()
//                    .replace(R.id.container, new RoomListFragment())
//                    .commit();

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_test) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFrag)
                    .commit();

    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
