package com.chicken.invasion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pedramshirmohammad on 16-05-16.
 */
public class HighScoreAdapter extends ArrayAdapter<Score> {

    public HighScoreAdapter(Context context, int resource, List<Score> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.highscore_content, null);
        }

        Score p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.nameID);
            TextView tt2 = (TextView) v.findViewById(R.id.scoreID);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            if (tt2 != null) {
                tt2.setText(Integer.toString(p.getPoints()));
            }

        }

        Button tstButton = (Button) v.findViewById(R.id.tstbutton);
        tstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }

}
