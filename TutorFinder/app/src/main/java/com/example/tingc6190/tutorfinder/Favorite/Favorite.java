package com.example.tingc6190.tutorfinder.Favorite;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tingc6190.tutorfinder.HomeActivity;
import com.example.tingc6190.tutorfinder.R;
import com.example.tingc6190.tutorfinder.Search.Tutor;

import java.util.ArrayList;

public class Favorite extends Fragment {

    HomeActivity homeActivity;
    private ArrayList<Tutor> favoriteTutors = new ArrayList<>();
    private FavoriteListener favoriteListener;

    public Favorite() {
    }

    public interface FavoriteListener
    {
        void getFavoriteTutor(Tutor mTutor);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FavoriteListener)
        {
            favoriteListener = (FavoriteListener) context;
        }
        else
        {
            Log.d("error", "must implement FavoriteListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        homeActivity = (HomeActivity) getActivity();

        favoriteTutors = homeActivity.getFavoriteTutors();

        return inflater.inflate(R.layout.content_favorite_screen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
        {
            //set up adapter
            //set adapter to list
            ListView listView = getView().findViewById(R.id.list_favorite);
            FavoriteTutorAdapter favoriteTutorAdapter = new FavoriteTutorAdapter(getContext(), favoriteTutors);
            listView.setAdapter(favoriteTutorAdapter);

            favoriteTutorAdapter.notifyDataSetChanged();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    favoriteListener.getFavoriteTutor(favoriteTutors.get(position));
                }
            });
        }
    }
}
