package com.itavery.forecast.domain.adaptors;

import com.itavery.forecast.request.ProductForecastRequest;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-10-09
 * https://github.com/helloavery
 */

public class ForecastAdaptor {

    public static DBObject toNewDBObject(ProductForecastRequest pfRequest){
        return new BasicDBObject().
                append("sku", pfRequest.getSku()).
                append("nationalInventory", pfRequest.getNationalInventory()).
                append("leadTime", pfRequest.getLeadTime()).
                append("inTransitQty", pfRequest.getInTransitQty()).
                append("forecastThreeMonths", pfRequest.getForecastThreeMonths()).
                append("forecastSixMonths", pfRequest.getForecastSixMonths()).
                append("forecastNineMonths", pfRequest.getForecastNineMonths()).
                append("salesOneMonth", pfRequest.getSalesOneMonth()).
                append("salesThreeMonths", pfRequest.getSalesThreeMonths()).
                append("salesSixMonths", pfRequest.getSalesSixMonths()).
                append("salesNineMonths", pfRequest.getSalesNineMonths()).
                append("minBank", pfRequest.getMinBank()).
                append("potentialIssues", pfRequest.getPotentialIssues()).
                append("piecesPastDue", pfRequest.getPiecesPastDue()).
                append("perfSixMonthAvg", pfRequest.getPerfSixMonthAvg()).
                append("perfTwelveMonthAvg", pfRequest.getPerfTwelveMonthAvg()).
                append("localBoQty", pfRequest.getLocalBoQty()).
                append("deckRisk", pfRequest.getDeckRisk()).
                append("oeConstraint",  pfRequest.getOeConstraint()).
                append("ppapRisk", pfRequest.getPpapRisk()).
                append("stopAutoBuy", pfRequest.getStopAutoBuy()).
                append("revStop", pfRequest.getRevStop()).
                append("backorder", pfRequest.getBackorder());
    }

    public static DBObject toDBObject(ProductForecastRequest pfRequest){
        return new BasicDBObject("_id", pfRequest.getProductForecastId()).
                append("sku", pfRequest.getSku()).
                append("nationalInventory", pfRequest.getNationalInventory()).
                append("leadTime", pfRequest.getLeadTime()).
                append("inTransitQty", pfRequest.getInTransitQty()).
                append("forecastThreeMonths", pfRequest.getForecastThreeMonths()).
                append("forecastSixMonths", pfRequest.getForecastSixMonths()).
                append("forecastNineMonths", pfRequest.getForecastNineMonths()).
                append("salesOneMonth", pfRequest.getSalesOneMonth()).
                append("salesThreeMonths", pfRequest.getSalesThreeMonths()).
                append("salesSixMonths", pfRequest.getSalesSixMonths()).
                append("salesNineMonths", pfRequest.getSalesNineMonths()).
                append("minBank", pfRequest.getMinBank()).
                append("potentialIssues", pfRequest.getPotentialIssues()).
                append("piecesPastDue", pfRequest.getPiecesPastDue()).
                append("perfSixMonthAvg", pfRequest.getPerfSixMonthAvg()).
                append("perfTwelveMonthAvg", pfRequest.getPerfTwelveMonthAvg()).
                append("localBoQty", pfRequest.getLocalBoQty()).
                append("deckRisk", pfRequest.getDeckRisk()).
                append("oeConstraint",  pfRequest.getOeConstraint()).
                append("ppapRisk", pfRequest.getPpapRisk()).
                append("stopAutoBuy", pfRequest.getStopAutoBuy()).
                append("revStop", pfRequest.getRevStop()).
                append("backorder", pfRequest.getBackorder());
    }
}
