package lafferty.com.abusivepermissions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.*;

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
                shellOut.setText(lsCall(shellInput.getText().toString()));
                shellOut.setMovementMethod(new ScrollingMovementMethod());
            }
        });


        //mainOut.setText(lsCall());
        //mainOut.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String lsCall(String input);

    // Used to load the 'native-lib' library on application startup.
    static
    {
        System.loadLibrary("native-lib");
    }
}
