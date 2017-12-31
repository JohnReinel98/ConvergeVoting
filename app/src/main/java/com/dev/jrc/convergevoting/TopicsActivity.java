package com.dev.jrc.convergevoting;

import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.dev.jrc.convergevoting.Adapters.GridViewAdapter;
import com.dev.jrc.convergevoting.Adapters.ListViewAdapter;
import com.dev.jrc.convergevoting.Models.ListTopics;

import java.util.ArrayList;
import java.util.List;

public class TopicsActivity extends AppCompatActivity {
    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private FloatingActionButton topicsFab;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<ListTopics> productList;
    private int currentViewMode = 0;

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        topicsFab = findViewById(R.id.topicsFab);

        stubList = (ViewStub) findViewById(R.id.stub_list);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) findViewById(R.id.topicsListView);
        gridView = (GridView) findViewById(R.id.topicsGridView);

        //get list of product
        getProductList();

        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item lick
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();
    }

    private void switchView() {

        if(VIEW_MODE_LISTVIEW == currentViewMode) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters() {
        if(VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = new ListViewAdapter(this, R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, productList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    public List<ListTopics> getProductList() {
        //pseudo code to get product, replace your code to get real product here
        productList = new ArrayList<>();
        productList.add(new ListTopics(R.drawable.ic_home, "Title 1", "This is description 1"));
        productList.add(new ListTopics(R.drawable.ic_home, "Title 2", "This is description 2"));
        productList.add(new ListTopics(R.drawable.ic_home, "Title 3", "This is description 3"));
        productList.add(new ListTopics(R.drawable.ic_home, "Title 4", "This is description 4"));
        productList.add(new ListTopics(R.drawable.ic_home, "Title 5", "This is description 5"));
        productList.add(new ListTopics(R.drawable.ic_home, "Title 6", "This is description 6"));
        productList.add(new ListTopics(R.drawable.ic_home, "Title 7", "This is description 7"));
        productList.add(new ListTopics(R.drawable.ic_home, "Title 8", "This is description 8"));
        productList.add(new ListTopics(R.drawable.ic_home, "Title 9", "This is description 9"));
        productList.add(new ListTopics(R.drawable.ic_home, "Title 10", "This is description 10"));

        return productList;
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
            Toast.makeText(getApplicationContext(), productList.get(position).getTitle() + " - " + productList.get(position).getDescription(), Toast.LENGTH_SHORT).show();
        }
    };

    public void SwitchStatePress(View v){
        if(VIEW_MODE_LISTVIEW == currentViewMode) {
            currentViewMode = VIEW_MODE_GRIDVIEW;
        } else {
            currentViewMode = VIEW_MODE_LISTVIEW;
        }
        //Switch view
        switchView();
        //Save view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("currentViewMode", currentViewMode);
        editor.commit();
    }
}
