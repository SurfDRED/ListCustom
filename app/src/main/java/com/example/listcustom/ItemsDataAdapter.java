package com.example.listcustom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemsDataAdapter extends BaseAdapter {

    public List<ItemData> getItems() {
        return items;
    }
    // Хранит список всех элементов списка
    private List<ItemData> items;
    // LayoutInflater – класс, который из layout-файла создает View-элемент.
    private LayoutInflater inflater;
    // Консруктор, в который передается контекст
    // для создания контролов из XML. И первоначальный список элементов.
    ItemsDataAdapter(Context context, List<ItemData> items) {
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    // Добавляет элемент в конец списка.
    void addItem(ItemData item) {
        items.add(item);
        notifyDataSetChanged();
    }
    // Очищаем список
    void Clear() {
        items.clear();
        notifyDataSetChanged();
    }
    // Удаляет элемент списка.
    void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    // Обязательный метод абстрактного класса BaseAdapter возвращает колличество элементов списка.
    @Override
    public int getCount() {
        return items.size();
    }

    // Возвращаем элемент списка на позиции - position
    @Override
    public ItemData getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }
    // Возвращает идентификатор. Обычно это position.
    @Override
    public long getItemId(int position) {
        return position;
    }
    // Создает или возвращает переиспользуемый View, с новыми данными для конкретной позиции.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_view, parent, false);
        }
        ItemData itemData = items.get(position);
        ImageView image = view.findViewById(R.id.icon);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);
        image.setImageDrawable(itemData.getImage());
        title.setText(itemData.getTitle());
        subtitle.setText(itemData.getSubtitle());
        return view;
    }
}