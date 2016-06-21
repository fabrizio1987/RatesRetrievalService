package com.entropay.ratestetrieval.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Pietro Cascio on 13/05/16.
 */
@Entity
@Table(name = "RATE", schema = "PUBLIC")
@XmlRootElement(name = "rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    // Store the filename from which this rate has been extracted
    @Column(name = "FILENAME", nullable = false)
    private String filename;

    // The conversion rate extracted
    @Column(name = "CONVERSION_RATE", nullable = false, precision = 10, scale = 7)
    private BigDecimal conversionRate;

    // The currency from which the rate will convert
    @Column(name = "BUY_CURR_CODE", length = 3, nullable = false)
    private String buyCurrencyCode;

    // The currency to which the rate will convert
    @Column(name = "SELL_CURR_CODE", length = 3, nullable = false)
    private String sellCurrencyCode;

    // The date when this rate could be used
    @Temporal(TemporalType.DATE)
    private Date validDate;

    // Empty constructor
    public Rate() {
    }

    public Rate(String filename, BigDecimal conversionRate, String buyCurrencyCode, String sellCurrencyCode, Date validDate) {
        this.filename = filename;
        this.conversionRate = conversionRate;
        this.buyCurrencyCode = buyCurrencyCode;
        this.sellCurrencyCode = sellCurrencyCode;
        this.validDate = validDate;
    }

    @XmlElement(name = "Id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "Filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String file) {
        this.filename = file;
    }

    @XmlElement(name = "ConversionRate")
    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
    }

    @XmlElement(name = "BuyCurrencyCode")
    public String getBuyCurrencyCode() {
        return buyCurrencyCode;
    }

    public void setBuyCurrencyCode(String buyCurrency) {
        this.buyCurrencyCode = buyCurrency;
    }

    @XmlElement(name = "SellCurrencyCode")
    public String getSellCurrencyCode() {
        return sellCurrencyCode;
    }

    public void setSellCurrencyCode(String sellCurrency) {
        this.sellCurrencyCode = sellCurrency;
    }

    @XmlElement(name = "ValidDate")
    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rate rate = (Rate) o;

        if (filename != null ? !filename.equals(rate.filename) : rate.filename != null) return false;
        if (conversionRate != null ? !conversionRate.equals(rate.conversionRate) : rate.conversionRate != null)
            return false;
        if (buyCurrencyCode != null ? !buyCurrencyCode.equals(rate.buyCurrencyCode) : rate.buyCurrencyCode != null) return false;
        if (sellCurrencyCode != null ? !sellCurrencyCode.equals(rate.sellCurrencyCode) : rate.sellCurrencyCode != null) return false;
        return !(validDate != null ? !validDate.equals(rate.validDate) : rate.validDate != null);

    }

    @Override
    public int hashCode() {
        int result = filename != null ? filename.hashCode() : 0;
        result = 31 * result + (conversionRate != null ? conversionRate.hashCode() : 0);
        result = 31 * result + (buyCurrencyCode != null ? buyCurrencyCode.hashCode() : 0);
        result = 31 * result + (sellCurrencyCode != null ? sellCurrencyCode.hashCode() : 0);
        result = 31 * result + (validDate != null ? validDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", conversionRate=" + conversionRate +
                ", buyCurrency='" + buyCurrencyCode + '\'' +
                ", sellCurrency='" + sellCurrencyCode + '\'' +
                ", validDate=" + validDate +
                '}';
    }
}
