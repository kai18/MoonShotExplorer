package tech.kaustubh.moonshotexplorer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static tech.kaustubh.moonshotexplorer.R.drawable.ic_description_black_24dp;


/**
 * Created by kaustubh on 28/01/17.
 */

public class DirListAdapter extends ArrayAdapter<String> {

    private Context activityContext;
    private ArrayList <String> dirList;
    private int row;

    public DirListAdapter(Context context,int row, ArrayList<String> dirList) {
        super(context, row, R.id.textView8, dirList);
        this.activityContext = context;
        this.dirList = dirList;
        this.row = row;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) activityContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myRow = inflater.inflate(row, parent, false);
        ImageView imageView = (ImageView) myRow.findViewById(R.id.imageView2);
        imageView.setImageResource(ic_description_black_24dp);
        TextView textView = (TextView) myRow.findViewById(R.id.textView8);
        textView.setText(dirList.get(position));
        return super.getView(position, convertView, parent);
    }
}
