package com.haya.currencyconverter1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CurrencyConversionActivity extends AppCompatActivity {

    TextView convert_from_dropdown_menu, convert_to_dropdown_menu, Conversion_Rate;
    EditText Edit_Amount_to_Convert_Value;
    ArrayList<String> arraylist;
    Dialog from_Dialog, to_Dialog;
    Button CONVERSION, EXIT;
    String Convert_from_Value, Convert_to_Value, Conversion_Value;
    String[] currency = {"AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN",
            "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTC", "BTN", "BWP", "BYN", "BYR", "BZD",
            "CAD", "CDF", "CHF", "CLF", "CLP", "CNY", "COP", "CRC", "CUC", "CUP", "CVE", "CZK",
            "DJF", "DKK", "DOP", "DZD",
            "EGP", "ERN", "ETB", "EUR",
            "FJD", "FKP",
            "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD",
            "HKD", "HNL", "HRK", "HTG", "HUF",
            "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK",
            "JEP", "JMD", "JOD", "JPY",
            "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT",
            "LAK", "LBP", "LKR", "LRD", "LSL", "LVL", "LYD",
            "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN",
            "NAD", "NGN", "NIO", "NOK", "NPR", "NZD",
            "OMR",
            "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG",
            "QAR",
            "RON", "RSD", "RUB", "RWF",
            "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "STD", "SVC", "SYP", "SZL",
            "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS",
            "UAH", "UGX", "USD", "UYU", "UZS",
            "VEF", "VND", "VUV",
            "WST",
            "XAF", "XAG", "XCD", "XDR", "XOF", "XPF",
            "YER",
            "ZAR", "ZMK", "ZMW", "ZWL"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_conversion);

        // Initialize UI components
        convert_from_dropdown_menu = findViewById(R.id.convert_from_dropdown_menu);
        convert_to_dropdown_menu = findViewById(R.id.convert_to_dropdown_menu);

        Conversion_Rate = findViewById(R.id.Conversion_Rate);
        CONVERSION = findViewById(R.id.CONVERSION);
        EXIT = findViewById(R.id.EXIT);
        Edit_Amount_to_Convert_Value = findViewById(R.id.Edit_Amount_to_Convert_Value);

        // Initialize arraylist with currency values
        arraylist = new ArrayList<>();
        for(String i : currency) {
             arraylist.add(i);
        }

        // Set click listener for "From" dropdown menu
        convert_from_dropdown_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show dialog for "From" dropdown menu
                from_Dialog = new Dialog(CurrencyConversionActivity.this);
                from_Dialog.setContentView(R.layout.from_spinner);
                from_Dialog.getWindow().setLayout(650, 800);
                from_Dialog.show();

                // Initialize EditText and ListView
                EditText edittext = from_Dialog.findViewById(R.id.edit_text);
                ListView listview = from_Dialog.findViewById(R.id.list_view);

                // Set adapter for ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(CurrencyConversionActivity.this, android.R.layout.simple_list_item_1, arraylist);
                listview.setAdapter(adapter);

                // Implement text watcher for filtering items in ListView
                edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

                // Set item click listener for ListView
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        convert_from_dropdown_menu.setText(adapter.getItem(position));
                        from_Dialog.dismiss();
                        Convert_from_Value = adapter.getItem(position);
                    }
                });
            }
        });

        // Set click listener for "To" dropdown menu
        convert_to_dropdown_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show dialog for "To" dropdown menu
                to_Dialog = new Dialog(CurrencyConversionActivity.this);
                to_Dialog.setContentView(R.layout.to_spinner);
                to_Dialog.getWindow().setLayout(650, 800);
                to_Dialog.show();

                // Initialize EditText and ListView
                EditText edittext = to_Dialog.findViewById(R.id.edit_text);
                ListView listview = to_Dialog.findViewById(R.id.list_view);

                // Set adapter for ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(CurrencyConversionActivity.this, android.R.layout.simple_list_item_1, arraylist);
                listview.setAdapter(adapter);

                // Implement text watcher for filtering items in ListView
                edittext.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

                // Set item click listener for ListView
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        convert_to_dropdown_menu.setText(adapter.getItem(position));
                        to_Dialog.dismiss();
                        Convert_to_Value = adapter.getItem(position);
                    }
                });
            }
        });

        // Set click listener for Conversion button
        CONVERSION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Get value from Edit_Amount_to_Convert_Value and convert it to Double
                    Double Edit_Amount_to_Convert_Value = Double.valueOf(CurrencyConversionActivity.this.Edit_Amount_to_Convert_Value.getText().toString());
                    // Call getConversionRate method
                    getConversionRate(Convert_from_Value, Convert_to_Value, Edit_Amount_to_Convert_Value);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Set click listener for Exit button
        EXIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Close_MainActivity();
            }
        });
    }

    // Method to get conversion rate from API
    public String getConversionRate(String convert_from_value, String convert_to_value, Double edit_amount_to_convert_value) {
        RequestQueue requestqueue = Volley.newRequestQueue(this);
        String url = "https://free.currconv.com/api/v7/convert?q=" + convert_from_value + "_" + convert_to_value + "&compact=ultra&apiKey=1498408f1536e92250ee";

        StringRequest stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonobject = null;
                try {
                    jsonobject = new JSONObject(response);
                    Double conversion_rate_value = round(((Double) jsonobject.get(convert_from_value + "_" + convert_to_value)), 2);
                    Conversion_Value = "" + round((conversion_rate_value * edit_amount_to_convert_value), 2);
                    Conversion_Rate.setText(Conversion_Value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestqueue.add(stringrequest);
        return null;
    }

    // Method to round a double value
    public static double round(double value, int currency) {
        if(currency < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(currency, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // Method to close page
    public void Close_MainActivity() {
        CurrencyConversionActivity.this.finish();
        System.exit(0);
    }
}

