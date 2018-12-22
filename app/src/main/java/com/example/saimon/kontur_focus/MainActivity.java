package com.example.saimon.kontur_focus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Fragment regFragment;
    TextView textView, textView2;
    Button action_voice, email_fragment;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textView = findViewById(R.id.textView);
                textView.setText(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                textView2 = findViewById(R.id.textView2);
                textView2.setText(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
