package ru.sberbankmobile.currencyconverter.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Executor;

import ru.sberbankmobile.currencyconverter.R;
import ru.sberbankmobile.currencyconverter.domain.DataMediator;
import ru.sberbankmobile.currencyconverter.domain.model.Currency;
import ru.sberbankmobile.currencyconverter.presentation.utils.IResourceWrapper;

class CurrencyConverterViewModel extends ViewModel {
    private final DataMediator mDataMediator;
    private final Executor mExecutor;
    private final IResourceWrapper mResourceWrapper;

    private final MutableLiveData<List<Currency>> mCurrencies = new MutableLiveData<>();
    private final MutableLiveData<String> mConvertedValue = new MutableLiveData<>();
    private final MutableLiveData<String> mConversionRate = new MutableLiveData<>();
    private final MutableLiveData<String> mErrors = new MutableLiveData<>();

    CurrencyConverterViewModel(
            @NonNull DataMediator dataMediator,
            @NonNull Executor executor,
            @NonNull IResourceWrapper resourceWrapper) {
        mDataMediator = dataMediator;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
    }

    void loadCurrencies() {
        final Currency ruble = new Currency("rub_id", "RUB", 1, mResourceWrapper.getString(R.string.russian_ruble), BigDecimal.ONE);
        mExecutor.execute(() -> {
            try {
                List<Currency> currencies = mDataMediator.loadCurrencies();
                if (!currencies.contains(ruble)) {
                    currencies.add(0, ruble);
                }
                mCurrencies.postValue(currencies);
            } catch (IOException e) {
                mErrors.postValue(mResourceWrapper.getString(R.string.error_loading_currencies));
            }
        });
    }

    @NonNull
    LiveData<List<Currency>> getCurrencies() {
        return mCurrencies;
    }

    @NonNull
    LiveData<String> getConversionRate() {
        return mConversionRate;
    }

    @NonNull
    LiveData<String> getConvertedText() {
        return mConvertedValue;
    }

    @NonNull
    LiveData<String> getErrors() {
        return mErrors;
    }

    void updateConversionRate(int fromCurrencyWithIndex, int toCurrencyWithIndex) {
        String updatedConversionRate = mDataMediator
                .formatConversionRate(mCurrencies.getValue(), fromCurrencyWithIndex, toCurrencyWithIndex);
        if (updatedConversionRate != null) {
            mConversionRate.setValue(updatedConversionRate);
        }
    }

    void convert(int fromCurrencyWithIndex, int toCurrencyWithIndex, @Nullable String amount) {
        List<Currency> currencies = mCurrencies.getValue();
        String convertedValue = mDataMediator.convert(currencies, fromCurrencyWithIndex, toCurrencyWithIndex, amount);
        if (convertedValue == null) {
            mErrors.setValue(mResourceWrapper.getString(R.string.conversion_error));
        } else {
            mConvertedValue.setValue(convertedValue);
        }
    }
}
