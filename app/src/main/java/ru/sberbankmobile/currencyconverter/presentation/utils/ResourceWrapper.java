package ru.sberbankmobile.currencyconverter.presentation.utils;

import android.content.res.Resources;

import androidx.annotation.NonNull;

public class ResourceWrapper implements IResourceWrapper {

    private final Resources mResources;

    public ResourceWrapper(@NonNull Resources resources) {
        mResources = resources;
    }

    @Override
    public String getString(int resId) {
        return mResources.getString(resId);
    }
}
