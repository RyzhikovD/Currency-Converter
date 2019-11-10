package ru.sberbankmobile.currencyconverter.domain;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import ru.sberbankmobile.currencyconverter.domain.model.Currency;

public interface ICurrenciesRepository {

    @NonNull
    List<Currency> loadCurrencies() throws IOException;
}
