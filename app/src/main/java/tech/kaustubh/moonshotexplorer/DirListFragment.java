package tech.kaustubh.moonshotexplorer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DirListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    ArrayAdapter <String> dirListAdapter;
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
        fileSystemHandler = new PlainTextFilesystem("mnt/");
        dirList = new ArrayList<String>(fileSystemHandler.ls("mnt/"));
        dirListAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, dirList);
        setListAdapter(dirListAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this.getActivity(),
                this.getListView().getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
        TextView path = (TextView) view;
        String dir = path.getText().toString();
        dirList.clear();
        dirListAdapter.clear();
        dirList = fileSystemHandler.ls(dir+"/");
        dirListAdapter.addAll(dirList);
        dirListAdapter.notifyDataSetChanged();
    }
}
