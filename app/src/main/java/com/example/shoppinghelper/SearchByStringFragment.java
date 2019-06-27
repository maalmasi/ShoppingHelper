package com.example.shoppinghelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class SearchByStringFragment extends Fragment {

    EditText etString;
    Button btnSearchByString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View Layout = inflater.inflate(R.layout.fragment_search_by_string, null);

        setUpUi(Layout);

        return Layout;
    }

    private void setUpUi(View layout) {
        etString = layout.findViewById(R.id.etSearchByString);
        btnSearchByString = layout.findViewById(R.id.btnSearchByString);
        btnSearchByString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etString.getText().toString();
                searchDatabaseString(query);
            }
        });

    }

    private void searchDatabaseString(String query) {
        Intent intent = new Intent(getActivity(), PricesActivity.class);
        intent.putExtra("query", query);
        intent.putExtra("op", "string");
        startActivity(intent);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
