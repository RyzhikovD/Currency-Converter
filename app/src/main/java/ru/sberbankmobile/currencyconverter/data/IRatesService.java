package ru.sberbankmobile.currencyconverter.data;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.sberbankmobile.currencyconverter.data.model.CurrenciesData;

interface IRatesService {

    @GET("scripts/XML_daily.asp")
    Call<CurrenciesData> loadCurrencies();
}
