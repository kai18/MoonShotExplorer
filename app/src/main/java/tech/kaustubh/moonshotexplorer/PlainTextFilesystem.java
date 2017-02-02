package tech.kaustubh.moonshotexplorer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by kaustubh on 18/12/16.
 */

public class PlainTextFilesystem implements FileSystem {

    boolean goingBack = false;
    private String rootName;
    private File root;
    private String pwd = null;

    private Stack <String> dirStack;

    public PlainTextFilesystem(String rootName)
    {
        this.rootName = rootName;
        root = new File(rootName);
        pwd = rootName;
        dirStack = new Stack<>();
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
        ArrayList<String> directoryNames = new ArrayList (directories.length);
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
        ArrayList <String> dirList = null;
        if(dir.equals(".."))
        {
            if(!dirStack.empty())
            {
                pwd = dirStack.peek();
                Log.d("Popping", dirStack.pop());
            }
        }
        else
            dirStack.push(pwd);

        if(!dir.equals(rootName) && !dir.equals(".."))
            pwd = pwd + dir;

        Log.d("Going", pwd);
        File pwdDir = new File(pwd);
        if(pwdDir != null)
            dirList = this.getNames(pwdDir.listFiles());

        return dirList;
    }

    @Override
    public int mkdir(String path) {
        try {
            File file = new File(pwd + path);
            if (file.createNewFile())
                return 1;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Intent opn(String path, Context context)
    {
        String filePath = getPwd()+path;
        File file = new File(filePath);
        if(file.isFile())
        {
            Intent fileIntent = new Intent(Intent.ACTION_VIEW);
            MimeTypeMap map = MimeTypeMap.getSingleton();
            String extension = map.getFileExtensionFromUrl(file.getAbsolutePath());
            String type = map.getMimeTypeFromExtension(extension);

            fileIntent.setDataAndType(FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName()+".provider", file), type);
            return  fileIntent;
        }
        return null;
    }

    @Override
    public String  getRoot(String dir) {
        return rootName;
    }

    @Override
    public int setRoot(String path) {
        this.rootName = path;
        return 0;
    }

    @Override
    public String getPwd() {
        return pwd;
    }

    @Override
    public void setPwd(String path) {
        this.pwd = path;
    }

    @Override
    public int cpy(String source, String destination) {
        return 0;
    }

}
