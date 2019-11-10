package ru.sberbankmobile.currencyconverter.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.sberbankmobile.currencyconverter.data.CurrenciesRepository;
import ru.sberbankmobile.currencyconverter.data.CurrencyConverter;
import ru.sberbankmobile.currencyconverter.domain.DataMediator;
import ru.sberbankmobile.currencyconverter.domain.ICurrenciesRepository;
import ru.sberbankmobile.currencyconverter.presentation.utils.ResourceWrapper;

class CurrencyViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Context mApplicationContext;

    CurrencyViewModelFactory(@NonNull Context context) {
        mApplicationContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (CurrencyConverterViewModel.class.equals(modelClass)) {
            ICurrenciesRepository currenciesRepository = new CurrenciesRepository(new CurrencyConverter());
            ResourceWrapper resourceWrapper = new ResourceWrapper(mApplicationContext.getResources());
            DataMediator dataMediator = new DataMediator(currenciesRepository);
            Executor executor = Executors.newSingleThreadExecutor();
            // noinspection unchecked
            return (T) new CurrencyConverterViewModel(
                    dataMediator,
                    executor,
                    resourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}
