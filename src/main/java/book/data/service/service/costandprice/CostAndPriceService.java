package book.data.service.service.costandprice;;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CostAndPriceService {

    public Double calculateEstimatedCostInCentsForUntranslatedInputCharCount(Long charCount) {
        Double cost = (charCount.doubleValue() * PER_MILLION_CHARS_TRUE_COST_ESTIMATE_CENTS.doubleValue())
            / ONE_MILLION.doubleValue();
        return cost;
    }

    public Double calculatePriceInCentsForUntranslatedInputCharCount(Long charCount) {
        Double price = calculateEstimatedCostInCentsForUntranslatedInputCharCount(charCount)
            * PRICE_MULTIPLIER.doubleValue();
        return price;
    }

    public Double calculatePriceInTokensForUntranslatedInputCharCount(Long charCount) {
        Double price = calculatePriceInCentsForUntranslatedInputCharCount(charCount)
            * PRICE_CENTS_TO_TOKENS_MULTIPLIER.doubleValue();
        return price;
    }

}
