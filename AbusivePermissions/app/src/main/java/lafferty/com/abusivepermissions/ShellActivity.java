package lafferty.com.abusivepermissions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Process;

public class ShellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);

        final TextView shellOut = (TextView) findViewById(R.id.shell_output);
        final EditText shellInput = (EditText) findViewById(R.id.shell_input);
        Button exeButton = (Button) findViewById(R.id.execute_button);

        exeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //System.out.println(shellInput.getText().toString());
                //System.out.println(lsCall(shellInput.getText().toString()));
                //shellOut.setText(lsCall(shellInput.getText().toString()));
                //shellOut.setMovementMethod(new ScrollingMovementMethod());

                //System.out.println(dCall());

                try
                {
                    String cmd[] = {"ls", "-a", "/storage/emulated/0"};
                    String cmd2[] = {"monkey", "/storage/emulated/0/Music/test.txt"};
                    String cmd3[] = {"ls", "-a", "/system/bin"};
                    String line = "";
                    Process p = Runtime.getRuntime().exec(cmd3);
                    p.waitFor();
                    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    while(line != null)
                    {
                        line = r.readLine();
                        if(line != null)
                        {
                            System.out.println(line);
                        }
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                //System.out.println(curlCall());
                //suCall();
                //shellOut.setText(suTestCall());
            }
        });


        //mainOut.setText(lsCall());
        //mainOut.setMovementMethod(new ScrollingMovementMethod());
    }


    //native libraries
    public native String lsCall(String input);
    public native String cCall();
    public native String curlCall();
    public native String dCall();
    public native String upCall();
    public native String webCall();
    public native String suCall();
    public native String suTestCall();

    // Used to load the 'native-lib' library on application startup.
    static
    {
        System.loadLibrary("native-lib");
    }
}
