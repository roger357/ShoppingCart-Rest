package com.example.shoppingCar.service;

import com.example.shoppingCar.ShoppingCarApplication;
import com.example.shoppingCar.dao.ShoppingCarProductRepository;
import com.example.shoppingCar.dao.ShoppingCarRepository;
import com.example.shoppingCar.exception.ShoppingCarException;
import com.example.shoppingCar.model.*;
import com.example.shoppingCar.model.enums.ShoppingCarState;
import com.example.shoppingCar.model.enums.ShoppingCarType;
import com.example.shoppingCar.service.helper.RuleApplierFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCarService {

    private static final Logger log = LoggerFactory.getLogger(ShoppingCarApplication.class);

    @Autowired
    private ShoppingCarRepository repository;
    @Autowired
    private ShoppingCarProductRepository shoppingCarProductRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PromotionDateService promotionDateService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCardProductService shoppingCardProductService;
    @Autowired
    private DiscountService discountService;

    public List<ShoppingCarProduct> getAllShoppingCarProductsByCar(Long shoppingCarId) {
        List<ShoppingCarProduct> clients = shoppingCarProductRepository.findAllByShoppingCar_Id(shoppingCarId);
        if(clients.size() > 0) {
            return clients;
        } else {
            return new ArrayList<ShoppingCarProduct>();
        }
    }

    public List<ShoppingCar> getClientShoppingCars(Long clientId) throws ShoppingCarException {
        Client client = clientService.getClientById(clientId);
        if(client != null){
            return repository.findAllByClientAndState(client, ShoppingCarState.PENDING);
        }else {
            throw new ShoppingCarException(String.format("No existen cliente con el id %s", clientId));
        }
    }

    /**
     * Busca un determinado carrito de compras
     * @param shoppingCarId Id del Carrito
     * @return
     */
    public ShoppingCar getShoppingCarById(Long shoppingCarId) throws ShoppingCarException {
        Optional shoppingCarOpt = repository.findById(shoppingCarId);
        if(shoppingCarOpt.isPresent()){
            return (ShoppingCar) shoppingCarOpt.get();
        }else{
                throw new ShoppingCarException("El Carrito no existe mas.");
        }
    }


    /**
     * Crea un nuevo carrito de compra aplicando las diferentes
     * reglas para determinar el tipo de carrito y reglas promocionales
     * @param clientId  El Id del cliente logeado en el WebApp
     * @return          Un nuevo carrito de compras
     */
    public ShoppingCar createNewShoppingCar(Long clientId) throws ShoppingCarException {

        ShoppingCarType shoppingCarType;

        try{
            Client client = clientService.getClientById(clientId);
            boolean clientVip = isClientVip(client.getId(), new DateTime());

            // Si no es Vip determinar si hay activa alguna promocion por fecha que pueda ser aplicada
            if(!clientVip){
                PromotionDate promotionDate = promotionDateService.getActivePromoByDate(Calendar.getInstance().getTime(), true);
                shoppingCarType = promotionDate != null ? ShoppingCarType.PROMODATE: ShoppingCarType.COMMON;
            }else{
                shoppingCarType = ShoppingCarType.VIP;
            }

            return createShoppingCar(client, shoppingCarType);
        }catch (Exception ex){
            throw new ShoppingCarException("Error durante la creacion del carrtio.");
        }

    }

    /**
     * Elimina (Logicamente) un carrito de compras
     * @param shoppingCarId
     * @throws ShoppingCarException
     */
    public ShoppingCar changeStateToShoppingCar(Long shoppingCarId, ShoppingCarState newState) throws ShoppingCarException {
        try{
            ShoppingCar shoppingCar = getShoppingCarById(shoppingCarId);
            shoppingCar.setState(newState);
            return repository.save(shoppingCar);
        }catch (Exception ex){
            throw new ShoppingCarException(String.format("Error durante el cambio de estado del carrtio a %s.", newState.name()));
        }
    }

    /**
     * Agrega productos al carrito
     * @param shoppingCarId
     * @param prodId
     * @return
     * @throws ShoppingCarException
     */
    public ShoppingCar addProductToShoppingCar(Long shoppingCarId, long prodId) throws ShoppingCarException {
        Product product =  productService.getProduct(prodId);
        if( product == null){
            throw new ShoppingCarException("El Producto solicitado no existe");
        }
        ShoppingCar shoppingCar = getShoppingCarById(shoppingCarId);
        ShoppingCarProduct shoppingCarProduct = shoppingCardProductService.createNewShoppingCarProduct(shoppingCar, product);
        //shoppingCar.getProducts().add(shoppingCarProduct);
        updateShoppingCarValue(shoppingCar);
        repository.save(shoppingCar);
        return shoppingCar;
    }

    /**
     * Baja productos que esten dentro del carrito
     * @param shoppingCarId
     * @param productCarId
     * @return
     * @throws ShoppingCarException
     */
    public ShoppingCar removeProductFromShoppingCar(Long shoppingCarId, Long productCarId) throws ShoppingCarException {
        if(!shoppingCardProductService.existsProductOnCar(productCarId)) {
            throw new ShoppingCarException("El producto no esta mas dentro del carrito de compras");
        }
        ShoppingCar shoppingCar = getShoppingCarById(shoppingCarId);
        removeProduct(shoppingCar.getProducts(), productCarId);
        shoppingCar = getShoppingCarById(shoppingCarId);
        updateShoppingCarValue(shoppingCar);
        repository.save(shoppingCar);
        return shoppingCar;
    }

    /**
     * Elimina un producto de la lista que coincida con el suministrado
     * @param products      Lista de productos
     * @param prodToRemove  Producto a eliminar
     */
    private void removeProduct(List<ShoppingCarProduct> products, Long prodToRemove) {
        Iterator prods = products.iterator();
        while(prods.hasNext()) {
            ShoppingCarProduct carProduct = (ShoppingCarProduct)prods.next();
            if(carProduct.getId().equals(prodToRemove)){
                prods.remove();
                shoppingCardProductService.removeProduct(carProduct);
            }
        }
    }

    private ShoppingCar createShoppingCar(Client client, ShoppingCarType shoppingCarType) {
        return repository.save(new ShoppingCar(client, shoppingCarType));
    }

    private void updateShoppingCarValue(ShoppingCar shoppingCar){
        int carProductsCount = shoppingCar.getProducts().size();
        Discount discount = discountService.findDiscountByProductsQuantity(carProductsCount);
        final RuleApplierFactory ruleApplierFactory = new RuleApplierFactory();
        if(discount != null && !shoppingCar.getProducts().isEmpty() && discount.isActive()){
            discount.getRules().stream().forEach(r -> {
                        if(r.getType() == shoppingCar.getType()){
                            try {
                                ruleApplierFactory.applyValueUpdateToCar(shoppingCar, r);
                            } catch (ShoppingCarException e) {
                                log.error(e.getMessage());
                            }
                        }else{
                            Double totalValue = shoppingCar.getProducts().stream()
                                    .mapToDouble(ShoppingCarProduct::getValue).sum();
                            shoppingCar.setValue(totalValue);
                        }

                    });
        } else {
            Double totalValue = shoppingCar.getProducts().stream()
                    .mapToDouble(ShoppingCarProduct::getValue).sum();
            shoppingCar.setValue(totalValue);
        }
    }

    /**
     * Busca los carrios anteriores a esta fecha
     * @param date
     * @return
     */
    public List<ShoppingCar> getShoppingCarsBefore(Date date) {
        return repository.findAllByCreationDateTimeBefore(date);
    }

    /**
     * Determina si un cliente es VIP. Es vip si compro dentro de los ultimos 30 dias un monto mayor a 10.000
     * @param clientID
     * @param finalDate
     * @return
     */
    private boolean isClientVip(Long clientID, DateTime finalDate) {
        Double espended = repository.getTotalSpendedInLasdays(clientID, finalDate.minusDays(30).toDate(), finalDate.toDate());
        if(espended == null || espended <= 10000){
            return false;
        }else{
            return true;
        }
    }

}
