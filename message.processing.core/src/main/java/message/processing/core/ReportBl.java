package message.processing.core;

import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;

import message.processing.model.MultipleProductsDTO;
import message.processing.model.PriceUnit;
import message.processing.model.ProductAdjustmentDTO;

@LocalBean
@Stateless
public class ReportBl {
	
	private static Logger log = Logger.getLogger(ReportBl.class);

	public void createShortReport(Map<String, List<MultipleProductsDTO>> productMap) {
		log.info("SHORT REPORT:");
		if(productMap == null || productMap.isEmpty()){
			log.warn("Product report is empty!");
			return;
		}
		productMap.forEach((key,value)->{
			int productNumber = 0;
			float productSum = 0.0f;
			for(MultipleProductsDTO p : value){
				productNumber += p.getNumberOfProducts();
				if(p.getProduct() != null){
					productSum += (p.getProduct().getPrice() * (p.getProduct().getPriceUnit().compareTo(PriceUnit.p)==0?100.0f:1.0f)); // we will convert in GPB
				}
			}
			log.info("Product name = ["+key+"], product number = ["+productNumber+"], total value = ["+productSum+"]");
		});
		log.info("End of short report");
	}

	public void createLongReport(List<ProductAdjustmentDTO> productAdjustmentList) {
		log.info("Application is pausing");
		log.info("ADJUSTMENTS REPORT");
		if(productAdjustmentList == null|| productAdjustmentList.isEmpty()){
			log.warn("Adjustments list is empty");
		}
		productAdjustmentList.forEach(adj-> {
			log.info("Adjustment type = [" +adj.getOperation()+"] adjustment amount = ["+(adj.getProduct()==null?null:adj.getProduct().getPrice()) +"], adjustment price unit = ["+(adj.getProduct()==null?null:adj.getProduct().getPriceUnit())+"]");
		});
		log.info("END OF ADJUSTMENT REPORT");
	}

	
}
