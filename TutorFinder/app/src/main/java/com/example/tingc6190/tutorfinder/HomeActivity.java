package com.example.tingc6190.tutorfinder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.tingc6190.tutorfinder.Account.Account;
import com.example.tingc6190.tutorfinder.Favorite.Favorite;
import com.example.tingc6190.tutorfinder.Message.Message;
import com.example.tingc6190.tutorfinder.Search.Search;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navbar_search:
                    //launch search fragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Search())
                            .commit();
                    return true;

                case R.id.navbar_messages:
                    //launch messages fragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Message())
                            .commit();
                    return true;

                case R.id.navbar_favorites:
                    //launch favorites fragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Favorite())
                            .commit();
                    return true;

                case R.id.navbar_account:
                    //launch account fragment
                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_container, new Account())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_container, new Search())
                .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
