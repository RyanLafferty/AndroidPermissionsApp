package lafferty.com.abusivepermissions;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import java.io.*;

/**
 * Created by Lafferty on 2017-03-22.
 */

public class FileActivity extends AppCompatActivity
{
    private TextView readable = null;
    private TextView writeable = null;
    public TextView fileOutput = null;
    private Button listRootButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        readable = (TextView) findViewById(R.id.readable);
        writeable = (TextView) findViewById(R.id.writeable);
        fileOutput = (TextView) findViewById(R.id.file_output);
        listRootButton = (Button) findViewById(R.id.list_root);

        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());


        boolean read = isExternalStorageReadable();
        boolean write = isExternalStorageWritable();

        if (read == true)
        {
            readable.setText("External is readable");
        }
        else
        {
            readable.setText("External is NOT readable");
        }

        if (write == true)
        {
            writeable.setText("External is writeable");
        }
        else
        {
            writeable.setText("External is NOT writeable");
        }


        listRootButton.setOnClickListener(createListDirButtonListener());
    }

    /* Check if we can write to external storage */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Check if we can read from external storage*/
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void listRoot()
    {
        File [] files = null;
        File sdcard = Environment.getExternalStorageDirectory();
        File dirs = new File(sdcard.getAbsolutePath());
        String dirOutput = "";
        int i = 0;

        if(dirs.exists())
        {
            files = dirs.listFiles();
        }

        for(i = 0; i < files.length; i++)
        {
            dirOutput += files[i];
        }

        fileOutput.setText(dirOutput);
    }

    protected View.OnClickListener createListDirButtonListener()
    {
        return (new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                File [] files = null;
                File sdcard = Environment.getExternalStorageDirectory();
                File dirs = new File(sdcard.getAbsolutePath());
                //File dirs = new File("/storage/emulated/");
                String dirOutput = "";
                int i = 0;

                System.out.println("a");

                if(dirs.exists())
                {
                    files = dirs.listFiles();
                    System.out.println(dirs.list());
                }

                if(files == null)
                {

                    System.out.println(dirs.getAbsolutePath());
                    System.out.println(dirs.list());
                }
                else
                {
                    for(i = 0; i < files.length; i++)
                    {
                        dirOutput += files[i].getName() + "\n";
                    }

                    fileOutput.setText(dirOutput);
                }

            }
        });
    }
}
