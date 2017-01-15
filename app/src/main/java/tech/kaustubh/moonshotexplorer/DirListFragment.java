package tech.kaustubh.moonshotexplorer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;


/**
 * A simple {@link Fragment} subclass.
 */
public class DirListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    ArrayAdapter <String> dirListAdapter;
    FileSystem fileSystemHandler;
    ArrayList<String> dirList;
    Stack<ArrayList<String>> dirStack;

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
        fileSystemHandler = new PlainTextFilesystem("mnt/sdcard/");
        dirList = new ArrayList<String>(fileSystemHandler.ls("mnt/sdcard/"));
        dirListAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, dirList);
        setListAdapter(dirListAdapter);
        getListView().setOnItemClickListener(this);
        dirStack = new Stack<ArrayList<String>>();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        TextView path = (TextView) view;
        String dir = path.getText().toString();
        ArrayList <String> tempDirList = new ArrayList<>(100);
        tempDirList.addAll(dirList);

        dirStack.push(tempDirList);
        dirList.clear();
        dirList = fileSystemHandler.ls(dir+"/");
        dirListAdapter.clear();

        if(dirList == null) {
            dirList = tempDirList;
            Toast.makeText(this.getActivity(),
                    "Directory is Empty",
                    Toast.LENGTH_SHORT).show();
        }

        dirListAdapter.addAll(dirList);
        dirListAdapter.notifyDataSetChanged();
    }

    public void goBack()
    {
        if(dirStack.empty())
            Toast.makeText(this.getActivity(), "Press Back again to exit.", Toast.LENGTH_SHORT);
        else
            fileSystemHandler.ls("..");
        Log.d("Called", "goBack");
    }

}
