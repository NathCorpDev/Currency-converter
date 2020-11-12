package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Spinner spn,spn1;
    EditText currencyTobeConverted;
    TextView currencyCoverted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyTobeConverted=findViewById(R.id.edit1);
        currencyCoverted=findViewById(R.id.edit2);
        Button btn=findViewById(R.id.button);
        String url="https://api.ratesapi.io/api/";
        spn=findViewById(R.id.sp);
        spn1=findViewById(R.id.sp1);
     ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(
             this, R.array.CurrencyCode , R.layout.support_simple_spinner_dropdown_item);
     adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
     spn.setAdapter(adapter);
     spn.setOnItemSelectedListener(this);
     spn1.setAdapter(adapter);
     spn1.setOnItemSelectedListener(this);





     btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             if(currencyTobeConverted.getText().toString().length()>0) {


                 Retrofit retrofit = new Retrofit.Builder()
                         .baseUrl("https://api.ratesapi.io/api/")
                         .addConverterFactory(GsonConverterFactory.create())
                         .build();
                 Api api = retrofit.create(Api.class);
                 Call<JsonObject> call = api.getValue(spn.getSelectedItem().toString());
                 call.enqueue(new Callback<JsonObject>() {

                     @Override
                     public void onFailure(Call<JsonObject> call, Throwable t) {
                         Log.d("error", t.toString());
                     }

                     @Override
                     public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                         if (!response.isSuccessful()) {
                             Toast.makeText(MainActivity.this, "No response", Toast.LENGTH_SHORT).show();
                             return;
                         }
                         JsonObject rate = response.body().getAsJsonObject("rates");

                         double currency = Double.parseDouble(currencyTobeConverted.getText().toString());
                         double result = Double.parseDouble(rate.get(spn1.getSelectedItem().toString()).toString());
                         double multi = currency * result;
                         currencyCoverted.setText(String.valueOf(multi));
                     }


                 });
             }else{
                 Toast.makeText(getBaseContext(),"Enter value",Toast.LENGTH_SHORT).show();
             }
         }
     });
       /* call.enqueue(new Callback<PostList>() {

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Log.d("error",t.toString());
            }
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"No response",Toast.LENGTH_SHORT).show();
                    return;
                }
                 ArrayList<String> cList= new ArrayList<>();

                assert response.body() != null;
                Results currencies=response.body().getResults();


                for(int i=1;i<=10;i++) {
                    cList.add(response.body().getResults().getKES().toString());
                }
            }


        });*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
