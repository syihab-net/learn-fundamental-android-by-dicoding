package com.ahmad.myquote;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmad.myquote.databinding.ActivityListQuotesBinding;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListQuotesActivity extends AppCompatActivity {

    private ActivityListQuotesBinding binding;
    public static final String TAG = ListQuotesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListQuotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.listQuotes.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.listQuotes.addItemDecoration(itemDecoration);

        getListQuotes();
    }

    private void getListQuotes() {
        binding.progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://quote-api.dicoding.dev/list";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                binding.progressBar.setVisibility(View.INVISIBLE);

                ArrayList<String> listQuote = new ArrayList<>();
                String result = new String(responseBody);
                Log.d(TAG, result);

                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String quote = jsonObject.getString("en");
                        String author = jsonObject.getString("author");

                        listQuote.add(String.format("\n%s\n - %s\n", quote, author));
                    }

                    binding.listQuotes.setAdapter(new QuoteAdapter(listQuote));
                } catch (JSONException e) {
                    Toast.makeText(ListQuotesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                binding.progressBar.setVisibility(View.INVISIBLE);

                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbidden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;
                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                }

                Toast.makeText(ListQuotesActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}