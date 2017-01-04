package tech.kaustubh.moonshotexplorer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        DirListFragment dirListFragment = new DirListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transactionManager = fragmentManager.beginTransaction();
        transactionManager.add(R.id.activity_main, dirListFragment);
        transactionManager.commit();
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

}
