package ru.sberbankmobile.currencyconverter.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import ru.sberbankmobile.currencyconverter.R;
import ru.sberbankmobile.currencyconverter.presentation.adapter.CurrencySpinnerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int SECOND_ITEM_INDEX = 1;
    private CurrencyConverterViewModel mViewModel;
    private Spinner mSpinnerFrom;
    private Spinner mSpinnerTo;
    private EditText mConvertingValue;
    private TextView mConvertedValue;
    private TextView mConversionRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupMVVM();
    }

    private void initViews() {
        mSpinnerFrom = findViewById(R.id.spinner_from);
        mSpinnerTo = findViewById(R.id.spinner_to);
        findViewById(R.id.button_convert).setOnClickListener(v -> mViewModel.convert(
                mSpinnerFrom.getSelectedItemPosition(),
                mSpinnerTo.getSelectedItemPosition(),
                mConvertingValue.getText().toString()
        ));
        mConvertingValue = findViewById(R.id.edit_text_value);
        mConvertedValue = findViewById(R.id.text_view_converted_value);
        mConversionRate = findViewById(R.id.text_view_conversion_rate);
        mSpinnerFrom.setOnItemSelectedListener(new OnCurrencySelectedListener());
        mSpinnerTo.setOnItemSelectedListener(new OnCurrencySelectedListener());
    }

    private void setupMVVM() {
        mViewModel = ViewModelProviders.of(this, new CurrencyViewModelFactory(this))
                .get(CurrencyConverterViewModel.class);
        mViewModel.getCurrencies().observe(this, currencies -> {
            mSpinnerFrom.setAdapter(new CurrencySpinnerAdapter(currencies));
            mSpinnerTo.setAdapter(new CurrencySpinnerAdapter(currencies));
            mSpinnerTo.setSelection(SECOND_ITEM_INDEX);
        });
        mViewModel.getConvertedText().observe(this, convertedText ->
                mConvertedValue.setText(convertedText));
        mViewModel.getConversionRate().observe(this, rate -> mConversionRate.setText(rate));
        mViewModel.getErrors().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_LONG).show());
        mViewModel.loadCurrencies();
    }

    private class OnCurrencySelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mViewModel.updateConversionRate(mSpinnerFrom.getSelectedItemPosition(),
                    mSpinnerTo.getSelectedItemPosition());
            mConvertedValue.setText(null);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
