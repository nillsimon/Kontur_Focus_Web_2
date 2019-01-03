package com.example.saimon.kontur_focus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Fragment regFragment;
    TextView textView;
    TextView textSearch;
    Button action_voice, email_fragment, buttonActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        initViews();
    }

    private void initListFragment() {
        Fragment fragment = new RegFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragmentContainer, fragment);
        ft.commit();
    }

    private void initViews() {
        regFragment = new Fragment();
        email_fragment = findViewById(R.id.reg_action);
        action_voice = findViewById(R.id.toolbar_voice);
        textSearch = findViewById(R.id.text_for_search);
        buttonActivity = findViewById(R.id.start_activity);

        buttonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textView = findViewById(R.id.text_for_search);
                textView.setText(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                textSearch = findViewById(R.id.text_for_search);
                textSearch.setText(newText);
                return true;
            }
        });
        return true;
    }
    private void integrationString() {
        String search = "ООО+Стройинвест+Москва";
        String s = "https://focus.kontur.ru/search?country=RU&query=";
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(s + search)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            integrationString();
            Toast.makeText(this, "Идет поиск", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.toolbar_voice) {
            Toast.makeText(this, "Голосовой ввод", Toast.LENGTH_SHORT).show();
            return true;
        }
            if (id == R.id.reg_action) {
                initListFragment();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
}
