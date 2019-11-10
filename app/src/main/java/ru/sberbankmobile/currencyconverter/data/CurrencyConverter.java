package ru.sberbankmobile.currencyconverter.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.sberbankmobile.currencyconverter.data.model.CurrenciesData;
import ru.sberbankmobile.currencyconverter.data.model.CurrencyData;
import ru.sberbankmobile.currencyconverter.domain.IConverter;
import ru.sberbankmobile.currencyconverter.domain.model.Currency;

public class CurrencyConverter
        implements IConverter<CurrenciesData, List<Currency>> {

    @NonNull
    @Override
    public List<Currency> convert(@NonNull CurrenciesData currencies) {
        List<Currency> result = new ArrayList<>();
        for (CurrencyData currency : currencies.getCurrencies()) {
            result.add(new Currency(
                    currency.getId(),
                    currency.getCharCode(),
                    currency.getNominal(),
                    currency.getName(),
                    currency.getValue()
            ));
        }
        return result;
    }
}
