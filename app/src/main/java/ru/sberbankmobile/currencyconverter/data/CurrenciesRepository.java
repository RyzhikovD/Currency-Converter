package ru.sberbankmobile.currencyconverter.data;

import androidx.annotation.NonNull;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.sberbankmobile.currencyconverter.data.model.CurrenciesData;
import ru.sberbankmobile.currencyconverter.domain.ICurrenciesRepository;
import ru.sberbankmobile.currencyconverter.domain.model.Currency;

public class CurrenciesRepository implements ICurrenciesRepository {

    private static final String BASE_URL = "http://www.cbr.ru";
    private final IRatesService mRatesApi;
    private final CurrencyConverter mCurrencyConverter;

    public CurrenciesRepository(@NonNull CurrencyConverter currencyConverter) {
        mCurrencyConverter = currencyConverter;
        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);
        // noinspection deprecation
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                .build();
        mRatesApi = retrofit.create(IRatesService.class);
    }

    @NonNull
    @Override
    public List<Currency> loadCurrencies() throws IOException {
        Call<CurrenciesData> listCall = mRatesApi.loadCurrencies();
        Response<CurrenciesData> response = listCall.execute();
        if (response.body() == null || response.errorBody() != null) {
            throw new IOException("Не удалось загрузить список валют");
        }
        return mCurrencyConverter.convert(response.body());
    }
}
