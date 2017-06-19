package bit.watset1.qrapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity
{
    Button btnQR;
    ListView lvMonths;
    TextView tvQr;
    HashMap<String, Integer> months;
    String currentQr;
    ArrayList<String> listMonths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listMonths = new ArrayList<String>();
        lvMonths = (ListView)findViewById(R.id.lvMonths);
        tvQr = (TextView)findViewById(R.id.tvDecode);
        btnQR = (Button)findViewById(R.id.btnQrDecode);
        btnQR.setOnClickListener(new QRButtonHandler());

        months = new HashMap<>();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            currentQr = data.getStringExtra("qr_message");
            if(!currentQr.contains("http")) {
                tvQr.setText(currentQr);
                if(months.containsKey(currentQr))
                    months.put(currentQr, months.get(currentQr) + 1);
                else
                    months.put(currentQr, 1);
                listMonths = ConvertMapToList();
                Collections.sort(listMonths);
                bindListView(listMonths, lvMonths);
            }
            else if(currentQr.contains("http"))
            {
                Uri internetUri = Uri.parse(currentQr);
                Intent internetIntent = new Intent(Intent.ACTION_VIEW, internetUri);
                startActivity(internetIntent);
            }
        }
    }

    private void bindListView(ArrayList<String> list, ListView lv)
    {
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(listAdapter);
    }

    private ArrayList<String> ConvertMapToList()
    {
        ArrayList<String> convertedMap = new ArrayList<String>();
        for(Map.Entry<String, Integer> entry : months.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            String text = key + ":   " + value;
            convertedMap.add(text);
        }

        return convertedMap;
    }

    public class QRButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent qrIntent = new Intent(MainActivity.this, QRActivity.class);
            startActivityForResult(qrIntent, 1);
        }
    }
}
