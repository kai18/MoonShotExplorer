package tech.kaustubh.moonshotexplorer;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by kaustubh on 18/12/16.
 */

public class PlainTextFilesystem implements FileSystem {

    boolean goingBack = false;
    String rootName;
    File root;
    String pwd;

    Stack <String> dirStack;
    public PlainTextFilesystem(String rootName)
    {
        this.rootName = rootName;
        root = new File(rootName);
        pwd = rootName;
    }

    public PlainTextFilesystem(File root)
    {
     this.root = root;
    }

    @Override
    public String getPath(String partialPath) {

        return null;
    }

    private ArrayList<String> getNames(File directories[])
    {
        int index = 0;
        ArrayList<String> directoryNames = new ArrayList<String>(directories.length);
        for (File file: directories)
            directoryNames.add(file.getName());

        return directoryNames;
    }


    @Override
    public String[] cd(String dir) {
        return new String[0];
    }

    @Override
    public ArrayList<String> ls(String dir) {

        String tempDir = pwd;

        if(dir != rootName && dir != "..")
            pwd = pwd + dir;

        if(dir == "..") {
            goingBack = true;
            pwd = dirStack.pop();
        }
        if(!goingBack)
            dirStack.push(pwd);
        File newDir = new File(pwd);
        File directories[] = null;

        ArrayList<String> directoryNames = null;

        Log.d("Path: ", pwd);

        if (newDir.exists()) {
            if (newDir.isDirectory()) {
                directories = newDir.listFiles();
                if (directories != null) {
                    directoryNames = this.getNames(directories);
                    return directoryNames;
                }
            }
        }
        pwd = tempDir;
        return null;
    }

    @Override
    public int mkdir(String path) {
        return 0;
    }

    @Override
    public int opn(String path) {
        return 0;
    }

    @Override
    public String  getRoot(String dir) {
        return null;
    }

    @Override
    public int setRoot(String path) {
        return 0;
    }

    @Override
    public String getPwd(String path) {
        return null;
    }

    @Override
    public void setPwd(String path) {

    }

    @Override
    public int cpy(String source, String destination) {
        return 0;
    }

}
