package tech.kaustubh.moonshotexplorer;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by kaustubh on 18/12/16.
 */

interface FileSystem {

    abstract String getPath(String partialPath);

    abstract String[] cd(String dir);

    abstract ArrayList<String> ls(String dir);

    abstract int mkdir(String path);

    abstract Intent opn(String path, Context context);

    abstract int setRoot(String path);

    String getRoot(String dir);

    abstract String getPwd();

    abstract void setPwd(String path);

    abstract int cpy(String source, String destination);

}
