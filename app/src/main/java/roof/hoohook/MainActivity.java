package roof.hoohook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import roof.hoohook.jni.HookInterface;

public class MainActivity extends AppCompatActivity {

    HookInterface hookInterface = new HookInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HookInterface.setup();

        int value1 = hookInterface.addNative(10, 11);
        int value2 = hookInterface.subNative(10, 11);

        TextView textView = (TextView)findViewById(R.id.value_tv);
        textView.setText("10 + 11 = " + value1 + "   10 - 11 = " + value2);
    }

    public HookInterface hookInterface() {
        return hookInterface;
    }
}
