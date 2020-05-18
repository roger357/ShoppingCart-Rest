package com.example.shoppingCar.service.helper;

import com.example.shoppingCar.exception.ShoppingCarException;
import com.example.shoppingCar.model.DiscountRule;
import com.example.shoppingCar.model.ShoppingCar;
import com.example.shoppingCar.model.ShoppingCarProduct;

public class RuleApplierFactory {

    public void applyValueUpdateToCar(ShoppingCar shoppingCar, DiscountRule discountRule) throws ShoppingCarException {
        if(discountRule == null){
            throw new ShoppingCarException("No existen reglas de descuentos para aplicar.");
        }else if(shoppingCar.getProducts() == null || shoppingCar.getProducts().isEmpty()) {
            throw new ShoppingCarException("Error al aplicar descuentos. NO hay productos en el carrito.");
        }

        Double totalValue = shoppingCar.getProducts().stream()
                .mapToDouble(ShoppingCarProduct::getValue).sum();

        switch (discountRule.getDiscountType()){
            case NET_VALUE:
                totalValue -= discountRule.getValue();
                shoppingCar.setValue(totalValue);
                break;
            case PERCENT:
                totalValue -= totalValue * (discountRule.getValue() / 100);
                shoppingCar.setValue(totalValue);
                break;
            default:
                break;
        }

    }

}
