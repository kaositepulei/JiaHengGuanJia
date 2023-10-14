package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class ScqppsjllbActivity extends AppCompatActivity {
    private SearchAdapter searchAdapter;
    private JSONObject qppsData;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scqppsjllb);
        Intent intent = getIntent();
        HashMap<String, Object> hashMap = (HashMap<String, Object>) intent.getSerializableExtra("qppsData");
        qppsData = new JSONObject(hashMap);
        Log.e("jhkj",qppsData.toString());
        jsonArray = new JSONArray();
        jsonArray.put(qppsData);

        RecyclerView searchView=findViewById(R.id.recyclerView);
        searchView.setScrollbarFadingEnabled(false);
        searchView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter();
        searchView.setAdapter(searchAdapter);

    }


    public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.qppsjl_item,parent,false);
            return new SearchViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

            Log.e("jhkj",jsonArray.toString()+"****************************");
            JSONObject dataObject = null;
            try {
                dataObject = jsonArray.getJSONObject(position);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                holder.qipingtiaoma.setText("气瓶条码: "+dataObject.getString("GasId"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                holder.kehu.setText("客户: "+dataObject.getString("Customer")+" | ");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                if (dataObject.getString("type").equals("1")) {
                    holder.fachuhuishou.setText("发出|");
                }
                if (dataObject.getString("type").equals("2")) {
                    holder.fachuhuishou.setText("回收|");
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                if (dataObject.getString("KongManLeiXing").equals("1")) {
                    holder.kongpingzhongping.setText("空瓶|");
                }
                if (dataObject.getString("KongManLeiXing").equals("1")) {
                    holder.kongpingzhongping.setText("重瓶|");
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            try {
                holder.gangpingzhongliang.setText("钢瓶重量: "+dataObject.getString("ZongZhong")+"KG");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }


        @Override
        public int getItemCount() {
            if (jsonArray!=null) {
                Log.e("jhkj",jsonArray.toString()+"****************************");
                return jsonArray.length();
            }else {
                Log.e("jhkj",jsonArray.toString()+"****************************");
                return 0;
            }
        }

        public class SearchViewHolder extends RecyclerView.ViewHolder {
            View view;
            TextView kehu;
            TextView qipingtiaoma;
            TextView fachuhuishou;
            TextView kongpingzhongping;
            TextView gangpingzhongliang;

            public SearchViewHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView;
                qipingtiaoma = itemView.findViewById(R.id.qipingtiaoma);
                kehu = itemView.findViewById(R.id.kehu);
                fachuhuishou = itemView.findViewById(R.id.fachuhuishou);
                kongpingzhongping = itemView.findViewById(R.id.kongpingzhongping);
                gangpingzhongliang = itemView.findViewById(R.id.gangpingzhongliang);
            }
        }

    }
}