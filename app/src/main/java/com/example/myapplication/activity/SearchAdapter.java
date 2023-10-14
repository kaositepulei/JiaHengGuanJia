package com.example.myapplication.activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private JSONArray jsonArray;

    public SearchAdapter() {
        this.jsonArray = jsonArray;
    }


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sbkz_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        Log.e("jhkj", String.valueOf(jsonArray));
        JSONObject dataObject = null;
        try {
            dataObject = jsonArray.getJSONObject(position);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            holder.shebeibianhao.setText(dataObject.getString("Key"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int getItemCount() {
        if (jsonArray != null) {
            Log.e("jhkj", jsonArray.toString() + "****************************");
            return jsonArray.length();
        } else {
            Log.e("jhkj", jsonArray.toString() + "****************************");
            return 0;
        }
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView shebeibianhao;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            shebeibianhao = itemView.findViewById(R.id.shebeibianhao);
        }
    }

}
