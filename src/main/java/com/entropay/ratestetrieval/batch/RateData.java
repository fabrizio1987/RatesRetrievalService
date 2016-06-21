package com.entropay.ratestetrieval.batch;

/**
 * Created by Pietro Cascio on 14/05/16.
 */
public class RateData {

    private String date;
    private String rate;
    private String buyCurrencyCode;
    private String sellCurrencyCode;
    private String plainLineRecord;
    private String filename;

    public RateData(String filename, String lineRecord) {
        if(lineRecord != null && lineRecord.length() == 22) {
            this.date = lineRecord.substring(0, 8);
            this.rate = lineRecord.substring(8, 16);
            this.buyCurrencyCode = lineRecord.substring(16, 19);
            this.sellCurrencyCode = lineRecord.substring(19, 22);
            this.plainLineRecord = lineRecord;
            this.filename = filename;
        }
    }

    public String getDate() {
        return date;
    }

    public String getRate() {
        return rate;
    }

    public String getBuyCurrencyCode() {
        return buyCurrencyCode;
    }

    public String getSellCurrencyCode() {
        return sellCurrencyCode;
    }

    public String getPlainLineRecord() {
        return plainLineRecord;
    }

    public String getFilename() {
        return filename;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setBuyCurrencyCode(String buyCurrencyCode) {
        this.buyCurrencyCode = buyCurrencyCode;
    }

    public void setSellCurrencyCode(String sellCurrencyCode) {
        this.sellCurrencyCode = sellCurrencyCode;
    }

    public void setPlainLineRecord(String plainLineRecord) {
        this.plainLineRecord = plainLineRecord;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "RateData{" +
                "date='" + date + '\'' +
                ", rate='" + rate + '\'' +
                ", buyCurrencyCode='" + buyCurrencyCode + '\'' +
                ", sellCurrencyCode='" + sellCurrencyCode + '\'' +
                ", plainLineRecord='" + plainLineRecord + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
