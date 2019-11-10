package ru.sberbankmobile.currencyconverter.data.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import java.math.BigDecimal;

import ru.sberbankmobile.currencyconverter.data.BigDecimalConverter;


@Root(name = "Valute", strict = false)
public class CurrencyData {

    @Attribute(name = "ID")
    private String mId;
    @Element(name = "NumCode")
    private int mNumCode;
    @Element(name = "CharCode")
    private String mCharCode;
    @Element(name = "Nominal")
    private long mNominal;
    @Element(name = "Name")
    private String mName;
    @Element(name = "Value")
    @Convert(BigDecimalConverter.class)
    private BigDecimal mValue;

    public String getId() {
        return mId;
    }

    public String getCharCode() {
        return mCharCode;
    }

    public long getNominal() {
        return mNominal;
    }

    public String getName() {
        return mName;
    }

    public BigDecimal getValue() {
        return mValue;
    }
}
