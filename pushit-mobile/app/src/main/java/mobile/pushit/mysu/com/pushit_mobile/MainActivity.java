package mobile.pushit.mysu.com.pushit_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mobile.pushit.mysu.com.pushit_mobile.services.PushService;
import mobile.pushit.mysu.com.pushit_mobile.services.PushServiceImpl;
import mobile.pushit.mysu.com.pushit_mobile.services.ResponseHandler;
import mobile.pushit.mysu.com.pushit_mobile.vo.PushContext;

public class MainActivity extends AppCompatActivity {

    public static final String SEPARATOR = "--";
    private PushService pushService;
    private String defaultUrl = "http://192.168.1.34:8080/rest/push";
    private PushContext context;
    private List<String> itemList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemList = new LinkedList<>();

        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, itemList);
        ListView sendList = findView(R.id.sendList);
        sendList.setAdapter(arrayAdapter);

        pushService = new PushServiceImpl(this.getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void connect(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void pushIt(View view){
        final String text = this.<EditText>findView(R.id.pushText).getText().toString();
        if(text != null && text.length()>0 ){
            pushService.push(context, text, new ResponseHandler() {
                @Override
                public void success(String response) {
                    itemList.add(0, text);
                    arrayAdapter.notifyDataSetChanged();
                    EditText pushText = findView(R.id.pushText);
                    pushText.setText("");
                }

                @Override
                public void error(String message) {
                    Toast t  = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    t.show();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null && Activity.RESULT_OK == resultCode) {

            context = createPushContext(scanResult.getContents());

            TextView status = findView(R.id.statusText);
            status.setText("PushId set!");

            findViewById(R.id.pushButton).setEnabled(true);

        }
    }

    private <T extends View> T findView(int viewId){
        return (T) findViewById(viewId);
    }

    @NonNull
    private PushContext createPushContext(String scanned) {
        String url;
        String pushId;
        if (scanned.contains(SEPARATOR)) {
            String[] params = scanned.split(SEPARATOR);
            pushId = params[1];
            url = params[0];
        } else {
            pushId = scanned;
            url = getDefaultUrl();
        }

        return new PushContext(pushId, url);
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }
}
