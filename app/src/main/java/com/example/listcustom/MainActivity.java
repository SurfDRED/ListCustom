package com.example.listcustom;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeLayout;
    private ItemsDataAdapter adapter;
    private List<Drawable> images = new ArrayList<>();
    private String[] title= new String[20];
    private String[] subtitle= new String[20];
    //SharedPreferences
    SharedPreferences myBook;
    static final String MY_BOOK = "my_book";
    static final String MY_BOOK_TITLE = "my_title";
    static final String MY_BOOK_SUBTITLE = "my_subtitle";
    //данные из стрингс
    private String eTitleText;    //текст сохраненный в Strings TitleText
    private String eSubtitleText;    //текст сохраненный в Strings TitleText
    private String sTitleText;    //текст сохраненный в SharedPreferences
    private String sSubtitleText;    //текст сохраненный в SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ListView listView = findViewById(R.id.list);
        View empty = findViewById(R.id.emptyList);
        swipeLayout = findViewById(R.id.swipe_refresh_layout);
        eTitleText = getString(R.string.large_TitleText);
        eSubtitleText = getString(R.string.large_SubTitleText);
        myBook = getSharedPreferences(MY_BOOK, MODE_PRIVATE);
        sTitleText = myBook.getString(MY_BOOK_TITLE, "");
        sSubtitleText = myBook.getString(MY_BOOK_SUBTITLE, "");
        // сравним эталоны с SharedPreferences
        if((!sTitleText.equals(eTitleText)) || (!sSubtitleText.equals(eSubtitleText))) {
            // внесем данные
            SharedPreferences.Editor myEditor = myBook.edit();
            myEditor.putString(MY_BOOK_TITLE, eTitleText);
            myEditor.putString(MY_BOOK_SUBTITLE, eSubtitleText);
            myEditor.apply();
            // прочитаем данные
            sTitleText = myBook.getString(MY_BOOK_TITLE, "");
            sSubtitleText = myBook.getString(MY_BOOK_SUBTITLE, "");
        }
        fillImages();
        title = titleContent();
        subtitle = subtitleContent();

        if (savedInstanceState != null) {
            ArrayList values = savedInstanceState.getStringArrayList("List");
            if (values != null) {
                // Создаем и устанавливаем адаптер на наш список
                adapter = new ItemsDataAdapter(this, values);
                listView.setAdapter(adapter);
                listView.setEmptyView(empty);
            }
        } else {
            adapter = new ItemsDataAdapter(this, null);
            generateItemData();
            listView.setAdapter(adapter);
            listView.setEmptyView(empty);
        }

        // При тапе по элементу списка будем показывать его данные
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showItemData(position);
            }
        });

        // обработка свайпа
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.Clear();
                generateItemData();
                swipeLayout.setRefreshing(false);
            }
        });
        // При долгом тапе по элементу списка будем удалять его
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //if (adapter.getCount() > 1) {
                //    textView.setVisibility(View.INVISIBLE);
                //} else {
                //    textView.setVisibility(View.VISIBLE);
                //}
                adapter.removeItem(position);
                return true;
            }
        });

    }
    // Заполним различными картинками, которые встроены в сам Android
    private void fillImages() {
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin01));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin02));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin03));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin04));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin05));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin06));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin07));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin08));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin09));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin10));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin11));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin12));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin13));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin14));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin15));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin16));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin17));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin18));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin19));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                R.drawable.scrin20));
    }
    // Сохраним данные при повороте
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList values = (ArrayList) adapter.getItems();
        outState.putStringArrayList("List", values);
    }
    private void generateItemData() {
        for (int i = 0; i < title.length; i++) {
            adapter.addItem(new ItemData(images.get(i),
                    title[i],
                    subtitle[i]));
        }
    }
    // Покажем сообщение с данными
    private void showItemData(int position) {
        ItemData itemData = adapter.getItem(position);
        Toast.makeText(MainActivity.this,
                "Title: " + itemData.getTitle() + "\n" +
                        "Subtitle: " + itemData.getSubtitle(),
                Toast.LENGTH_SHORT).show();
    }
    @NonNull
    private String[] titleContent() {
        return sTitleText.split("\n\n ");
    }
    @NonNull
    private String[] subtitleContent() {
        return sSubtitleText.split("\n\n ");
    }
}