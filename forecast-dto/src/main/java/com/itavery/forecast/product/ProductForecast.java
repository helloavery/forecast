package com.itavery.forecast.product;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-02-05
 * https://github.com/helloavery
 */

public class ProductForecast implements Serializable {

    private Integer productForecastId;
    private Integer sku;
    private Integer nationalInventory;
    private Integer leadTime;
    private Integer inTransitQty;
    private Integer forecastThreeMonths;
    private Integer forecastSixMonths;
    private Integer forecastNineMonths;
    private Integer salesOneMonth;
    private Integer salesThreeMonths;
    private Integer salesSixMonths;
    private Integer salesNineMonths;
    private Integer minBank;
    private String potentialIssues;
    private Integer piecesPastDue;
    private Double perfSixMonthAvg;
    private Double perfTwelveMonthAvg;
    private Integer localBoQty;
    private String deckRisk;
    private String oeConstraint;
    private String ppapRisk;
    private String stopAutoBuy;
    private String revStop;
    private String backorder;
    private Integer userId;

    public Integer getProductForecastId() {
        return productForecastId;
    }

    public void setProductForecastId(Integer productForecastId) {
        this.productForecastId = productForecastId;
    }

    public Integer getSku() {
        return sku;
    }

    public void setSku(Integer sku) {
        this.sku = sku;
    }

    public Integer getNationalInventory() {
        return nationalInventory;
    }

    public void setNationalInventory(Integer nationalInventory) {
        this.nationalInventory = nationalInventory;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Integer getInTransitQty() {
        return inTransitQty;
    }

    public void setInTransitQty(Integer inTransitQty) {
        this.inTransitQty = inTransitQty;
    }

    public Integer getForecastThreeMonths() {
        return forecastThreeMonths;
    }

    public void setForecastThreeMonths(Integer forecastThreeMonths) {
        this.forecastThreeMonths = forecastThreeMonths;
    }

    public Integer getForecastSixMonths() {
        return forecastSixMonths;
    }

    public void setForecastSixMonths(Integer forecastSixMonths) {
        this.forecastSixMonths = forecastSixMonths;
    }

    public Integer getForecastNineMonths() {
        return forecastNineMonths;
    }

    public void setForecastNineMonths(Integer forecastNineMonths) {
        this.forecastNineMonths = forecastNineMonths;
    }

    public Integer getSalesOneMonth() {
        return salesOneMonth;
    }

    public void setSalesOneMonth(Integer salesOneMonth) {
        this.salesOneMonth = salesOneMonth;
    }

    public Integer getSalesThreeMonths() {
        return salesThreeMonths;
    }

    public void setSalesThreeMonths(Integer salesThreeMonths) {
        this.salesThreeMonths = salesThreeMonths;
    }

    public Integer getSalesSixMonths() {
        return salesSixMonths;
    }

    public void setSalesSixMonths(Integer salesSixMonths) {
        this.salesSixMonths = salesSixMonths;
    }

    public Integer getSalesNineMonths() {
        return salesNineMonths;
    }

    public void setSalesNineMonths(Integer salesNineMonths) {
        this.salesNineMonths = salesNineMonths;
    }

    public Integer getMinBank() {
        return minBank;
    }

    public void setMinBank(Integer minBank) {
        this.minBank = minBank;
    }

    public String getPotentialIssues() {
        return potentialIssues;
    }

    public void setPotentialIssues(String potentialIssues) {
        this.potentialIssues = potentialIssues;
    }

    public Integer getPiecesPastDue() {
        return piecesPastDue;
    }

    public void setPiecesPastDue(Integer piecesPastDue) {
        this.piecesPastDue = piecesPastDue;
    }

    public Double getPerfSixMonthAvg() {
        return perfSixMonthAvg;
    }

    public void setPerfSixMonthAvg(Double perfSixMonthAvg) {
        this.perfSixMonthAvg = perfSixMonthAvg;
    }

    public Double getPerfTwelveMonthAvg() {
        return perfTwelveMonthAvg;
    }

    public void setPerfTwelveMonthAvg(Double perfTwelveMonthAvg) {
        this.perfTwelveMonthAvg = perfTwelveMonthAvg;
    }

    public Integer getLocalBoQty() {
        return localBoQty;
    }

    public void setLocalBoQty(Integer localBoQty) {
        this.localBoQty = localBoQty;
    }

    public String getDeckRisk() {
        return deckRisk;
    }

    public void setDeckRisk(String deckRisk) {
        this.deckRisk = deckRisk;
    }

    public String getOeConstraint() {
        return oeConstraint;
    }

    public void setOeConstraint(String oeConstraint) {
        this.oeConstraint = oeConstraint;
    }

    public String getPpapRisk() {
        return ppapRisk;
    }

    public void setPpapRisk(String ppapRisk) {
        this.ppapRisk = ppapRisk;
    }

    public String getStopAutoBuy() {
        return stopAutoBuy;
    }

    public void setStopAutoBuy(String stopAutoBuy) {
        this.stopAutoBuy = stopAutoBuy;
    }

    public String getRevStop() {
        return revStop;
    }

    public void setRevStop(String revStop) {
        this.revStop = revStop;
    }

    public String getBackorder() {
        return backorder;
    }

    public void setBackorder(String backorder) {
        this.backorder = backorder;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductForecast productForecast = (ProductForecast) o;
        return Objects.equals(productForecastId, productForecast.getProductForecastId()) &&
                Objects.equals(sku, productForecast.getSku()) &&
                Objects.equals(nationalInventory, productForecast.getNationalInventory()) &&
                Objects.equals(leadTime, productForecast.getLeadTime()) &&
                Objects.equals(inTransitQty, productForecast.getInTransitQty()) &&
                Objects.equals(forecastThreeMonths, productForecast.getForecastThreeMonths()) &&
                Objects.equals(forecastSixMonths, productForecast.getForecastSixMonths()) &&
                Objects.equals(forecastNineMonths, productForecast.getForecastNineMonths()) &&
                Objects.equals(minBank, productForecast.getMinBank()) &&
                Objects.equals(potentialIssues, productForecast.getPotentialIssues()) &&
                Objects.equals(piecesPastDue, productForecast.getPiecesPastDue()) &&
                Objects.equals(perfSixMonthAvg, productForecast.getPerfSixMonthAvg()) &&
                Objects.equals(perfTwelveMonthAvg, productForecast.getPerfTwelveMonthAvg()) &&
                Objects.equals(localBoQty, productForecast.getLocalBoQty()) &&
                Objects.equals(deckRisk, productForecast.getDeckRisk()) &&
                Objects.equals(oeConstraint, productForecast.getOeConstraint()) &&
                Objects.equals(ppapRisk, productForecast.getPpapRisk()) &&
                Objects.equals(stopAutoBuy, productForecast.getStopAutoBuy()) &&
                Objects.equals(revStop, productForecast.getRevStop()) &&
                Objects.equals(backorder, productForecast.getBackorder()) &&
                Objects.equals(userId, productForecast.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productForecastId, sku, nationalInventory, leadTime, inTransitQty, forecastThreeMonths, forecastSixMonths, forecastNineMonths,
                minBank, potentialIssues, piecesPastDue, perfSixMonthAvg, perfTwelveMonthAvg, localBoQty, deckRisk, oeConstraint, ppapRisk, stopAutoBuy,
                revStop, backorder, userId);
    }
}
