package com.itavery.forecast.domain.assemblers;

import com.itavery.forecast.domain.mithra.product.ProductForecastDB;
import com.itavery.forecast.product.ProductForecastDTO;
import org.springframework.stereotype.Component;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-10-09
 * https://github.com/helloavery
 */

@Component
public class ForecastAssembler {

    public ProductForecastDTO covertProductForecastToDTO(ProductForecastDB productForecastDB) {
        ProductForecastDTO productForecastDTO = new ProductForecastDTO();
        productForecastDTO.setSku(productForecastDB.getSku());
        productForecastDTO.setNationalInventory(productForecastDB.getNationalInventory());
        productForecastDTO.setLeadTime(productForecastDB.getLeadTime());
        productForecastDTO.setInTransitQty(productForecastDB.getInTransitQty());
        productForecastDTO.setForecastThreeMonths(productForecastDB.getForecastThreeMonths());
        productForecastDTO.setForecastSixMonths(productForecastDB.getForecastSixMonths());
        productForecastDTO.setForecastNineMonths(productForecastDB.getForecastNineMonths());
        productForecastDTO.setSalesOneMonth(productForecastDB.getSalesOneMonth());
        productForecastDTO.setSalesThreeMonths(productForecastDB.getSalesThreeMonths());
        productForecastDTO.setSalesSixMonths(productForecastDB.getSalesSixMonths());
        productForecastDTO.setSalesNineMonths(productForecastDB.getSalesNineMonths());
        productForecastDTO.setMinBank(productForecastDB.getMinBank());
        productForecastDTO.setPotentialIssues(productForecastDB.getPotentialIssues());
        productForecastDTO.setPiecesPastDue(productForecastDB.getPiecesPastDue());
        productForecastDTO.setPerfSixMonthAvg(productForecastDB.getPerfSixMonthAvg());
        productForecastDTO.setPerfTwelveMonthAvg(productForecastDB.getPerfTwelveMonthAvg());
        productForecastDTO.setLocalBoQty(productForecastDB.getLocalBoQty());
        productForecastDTO.setDeckRisk(productForecastDB.getDeckRisk());
        productForecastDTO.setOeConstraint(productForecastDB.getOeConstraint());
        productForecastDTO.setPpapRisk(productForecastDB.getPpapRisk());
        productForecastDTO.setStopAutoBuy(productForecastDB.getStopAutoBuy());
        productForecastDTO.setRevStop(productForecastDB.getRevStop());
        productForecastDTO.setBackorder(productForecastDB.getBackorder());
        return productForecastDTO;
    }

    public ProductForecastDB covertToProductForecastDB(ProductForecastDTO productForecastDTO) {
        ProductForecastDB productForecastDB = new ProductForecastDB();
        productForecastDB.setSku(productForecastDTO.getSku());
        productForecastDB.setNationalInventory(productForecastDTO.getNationalInventory());
        productForecastDB.setLeadTime(productForecastDTO.getLeadTime());
        productForecastDB.setInTransitQty(productForecastDTO.getInTransitQty());
        productForecastDB.setForecastThreeMonths(productForecastDTO.getForecastThreeMonths());
        productForecastDB.setForecastSixMonths(productForecastDTO.getForecastSixMonths());
        productForecastDB.setForecastNineMonths(productForecastDTO.getForecastNineMonths());
        productForecastDB.setSalesOneMonth(productForecastDTO.getSalesOneMonth());
        productForecastDB.setSalesThreeMonths(productForecastDTO.getSalesThreeMonths());
        productForecastDB.setSalesSixMonths(productForecastDTO.getSalesSixMonths());
        productForecastDB.setSalesNineMonths(productForecastDTO.getSalesNineMonths());
        productForecastDB.setMinBank(productForecastDTO.getMinBank());
        productForecastDB.setPotentialIssues(productForecastDTO.getPotentialIssues());
        productForecastDB.setPiecesPastDue(productForecastDTO.getPiecesPastDue());
        productForecastDB.setPerfSixMonthAvg(productForecastDTO.getPerfSixMonthAvg());
        productForecastDB.setPerfTwelveMonthAvg(productForecastDTO.getPerfTwelveMonthAvg());
        productForecastDB.setLocalBoQty(productForecastDTO.getLocalBoQty());
        productForecastDB.setDeckRisk(productForecastDTO.getDeckRisk());
        productForecastDB.setOeConstraint(productForecastDTO.getOeConstraint());
        productForecastDB.setPpapRisk(productForecastDTO.getPpapRisk());
        productForecastDB.setStopAutoBuy(productForecastDTO.getStopAutoBuy());
        productForecastDB.setRevStop(productForecastDTO.getRevStop());
        productForecastDB.setBackorder(productForecastDTO.getBackorder());
        return productForecastDB;
    }
}
