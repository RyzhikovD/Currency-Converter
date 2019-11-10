package ru.sberbankmobile.currencyconverter.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import ru.sberbankmobile.currencyconverter.domain.model.Currency;

public class CurrencySpinnerAdapter extends BaseAdapter {

    private final List<Currency> mCurrencies;

    public CurrencySpinnerAdapter(@NonNull List<Currency> currencies) {
        mCurrencies = currencies;
    }

    @Override
    public int getCount() {
        return mCurrencies.size();
    }

    @Override
    public Currency getItem(int position) {
        return mCurrencies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            CurrencySpinnerAdapter.ViewHolder viewHolder = new CurrencySpinnerAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Currency currency = getItem(position);
        if (currency != null) {
            String text = currency.getName();
            holder.mCurrencyName.setText(text);
        }
        return convertView;
    }

    private static class ViewHolder {
        private final TextView mCurrencyName;

        private ViewHolder(View view) {
            mCurrencyName = view.findViewById(android.R.id.text1);
        }
    }
}