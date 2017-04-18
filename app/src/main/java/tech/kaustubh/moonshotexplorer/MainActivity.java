package tech.kaustubh.moonshotexplorer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String permissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    String storage[] = {Environment.getExternalStorageDirectory().getAbsolutePath()};
    DirListFragment dirListFragment;
    ArrayAdapter<String> drawerAdapter = null;
    DrawerLayout drawerLayout = null;
    ListView drawerList = null;
    Bundle savedInstanceState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerList = (ListView) drawerLayout.findViewById(R.id.drawerList);

        if (drawerList == null)
            Log.d("Adapter", "null");
        drawerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, storage);

        drawerList.setAdapter(drawerAdapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        ActivityCompat.requestPermissions(this, permissions, 1);

        dirListFragment = new DirListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transactionManager = fragmentManager.beginTransaction();
        transactionManager.add(R.id.activity_main, dirListFragment);
        transactionManager.commit();
        drawerList.setOnItemClickListener(new DrawerAdaptor(dirListFragment));
        List<StorageUtils.StorageInfo> list = StorageUtils.getStorageList();
        for (StorageUtils.StorageInfo indo: list)
        {
            Log.d("Storage", indo.getDisplayName());

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        dirListFragment.goBack();
        Log.d("Button","Back");
    }

}
