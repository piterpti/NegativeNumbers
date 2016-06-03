package com.example.ruzik.liczbyujemne.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ruzik.liczbyujemne.Classes.DifficultLevel;
import com.example.ruzik.liczbyujemne.Classes.DifficultLevelAdapter;
import com.example.ruzik.liczbyujemne.MainActivity;
import com.example.ruzik.liczbyujemne.R;


public class DifficultLevelFragment extends Fragment {


    private ListView list;

    public DifficultLevelFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_difficult_level, container, false);
        DifficultLevelAdapter adapter = new DifficultLevelAdapter(getActivity(), MainActivity.difficultLevels);
        for(DifficultLevel diffLevel : MainActivity.difficultLevels) {
            if(diffLevel.equals(MainActivity.gameStatus.getDifficultLevel())) {
                diffLevel.setActive(true);
            } else {
                diffLevel.setActive(false);
            }
        }
        list = (ListView) view.findViewById(R.id.difficultLevels);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new ListHandler());
        return view;
    }

    private void BackToMenu() {
        MenuFragment main_menu_fragment = new MenuFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, main_menu_fragment);
        transaction.commit();
    }

    public class ListHandler implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             MainActivity.gameStatus.setDifficultLevel((DifficultLevel) list.getItemAtPosition(position));
            ((DifficultLevel) list.getItemAtPosition(position)).setActive(true);
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(MainActivity.DIFFICULT_LEVEL_KEY, MainActivity.gameStatus.getDifficultLevel().getId());
            editor.commit();
            BackToMenu();
        }
    }

}
