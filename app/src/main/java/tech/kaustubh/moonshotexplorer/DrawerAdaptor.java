package tech.kaustubh.moonshotexplorer;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by kaustubh on 4/18/17.
 */

public class DrawerAdaptor implements ListView.OnItemClickListener {

    String internalStorage = null;

    FileSystem fileSystemHandler = null;
    DirListFragment dirListFragment = null;
    public DrawerAdaptor(DirListFragment dirListFragment)
    {
        internalStorage = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d("External", internalStorage);
        Log.d("Root", Environment.getRootDirectory().getAbsolutePath());
        this.dirListFragment = dirListFragment;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("CLicked!!", view.toString());
        this.fileSystemHandler = dirListFragment.getFileSystemHandler();
        TextView lv = (TextView)view;
        String dir = (String) lv.getText();

        Log.d("dir", dir);
        //fileSystemHandler.setPwd("null");
        //Intent fileIntent = fileSystemHandler.opn(dir, dirListFragment.getContext());
        dirListFragment.displayDirList(fileSystemHandler.ls(dir));
    }

}
