package tech.kaustubh.moonshotexplorer;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Fragment that contains the directory contents and listing.
 * It uses the Filesystem object to fetch directory contents
 * and display them in a ListView, using the DirListAdapter.
 */
public class DirListFragment extends ListFragment implements ListView.OnItemClickListener {

    DirListAdapter dirListAdapter;
    FileSystem fileSystemHandler;
    ArrayList<String> dirList;

    public DirListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_dir_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fileSystemHandler = PlainTextFilesystem.getFilesystemHandler();
        dirList = new ArrayList<>(fileSystemHandler.ls(Environment.getExternalStorageDirectory()
                .getAbsolutePath()));
        dirListAdapter = new DirListAdapter(this.getActivity(), R.layout.dir_list, dirList);
        setListAdapter(dirListAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        //this.goBack();
        super.onPause();
    }

    public FileSystem getFileSystemHandler()
    {
        return fileSystemHandler;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        RelativeLayout relativeLayout = (RelativeLayout) view;
        TextView path = (TextView) relativeLayout.findViewById(R.id.textView8);
        String dir = path.getText().toString();
        Log.d("Dir", dir);
        Intent fileIntent = fileSystemHandler.opn(dir, getContext());
        if(fileIntent != null) {
            this.startActivity(fileIntent);
            return;
        }
        else {
            //fileSystemHandler.setPwd("null");
            displayDirList(fileSystemHandler.ls("/"+dir));
        }
    }

    public void goBack()
    {
        Log.d("Going", "Back1");
        displayDirList(fileSystemHandler.ls(".."));
        Log.d("Called", "goBack");
    }

    public void displayDirList(ArrayList<String> list)
    {

        ArrayList<String> tempDirList = new ArrayList<>(dirList.size());
        tempDirList.addAll(dirList);
        dirList.clear();
        dirListAdapter.clear();
        dirList.addAll(list);
        dirListAdapter.notifyDataSetChanged();

        if(list.size() == 0) {
            Toast.makeText(this.getActivity(),
                    "Directory is Empty",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
