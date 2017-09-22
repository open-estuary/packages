package com.htsat.cart.serviceimpl;

import com.htsat.cart.dao.REcCartskuMapper;
import com.htsat.cart.dao.REcShoppingcartMapper;
import com.htsat.cart.dao.REcSkuMapper;
import com.htsat.cart.dto.SKUDTO;
import com.htsat.cart.dto.ShoppingCartDTO;
import com.htsat.cart.model.REcCartsku;
import com.htsat.cart.model.REcCartskuKey;
import com.htsat.cart.model.REcShoppingcart;
import com.htsat.cart.model.REcSku;
import com.htsat.cart.service.IRedisService;
import com.htsat.cart.service.IShoppingCartService;
import com.htsat.cart.utils.ComputeUtils;
import com.htsat.cart.utils.ConvertToDTO;
import com.htsat.cart.utils.SerializeUtil;
import com.htsat.cart.utils.SortList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    private Logger logger = Logger.getLogger(ShoppingCartServiceImpl.class);

    @Autowired
    private REcShoppingcartMapper shoppingcartMapper;

    @Autowired
    private REcCartskuMapper cartskuMapper;

    @Autowired
    private REcSkuMapper skuMapper;

    @Autowired
    private IRedisService redisService;

    private Jedis getJedis(){
        return redisService.getResource();
    }

    /******************************************create*********************************************/
    /**
     * create shopping cart
     * @param shoppingCartDTO
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public ShoppingCartDTO addShoppingCartAndSKU(ShoppingCartDTO shoppingCartDTO) throws Exception {
        REcShoppingcart shoppingcart = createShoppingCart(shoppingCartDTO.getUserId(), shoppingCartDTO.getCurrency(), shoppingCartDTO.getSkudtoList());
        List<REcSku> skuList = createCartSKU(shoppingCartDTO.getUserId(), shoppingCartDTO.getSkudtoList());
        ShoppingCartDTO returnShoppingCartDTO = ConvertToDTO.convertToShoppingDTO(shoppingcart, skuList);
        return returnShoppingCartDTO;
    }

    private REcShoppingcart createShoppingCart(int userId, String currency, List<SKUDTO> skuDTOList) throws Exception {
        int quantity = ComputeUtils.computeNumber(skuDTOList);
        float discount = ComputeUtils.computeDiscount(skuDTOList);
        BigDecimal totalPrice = ComputeUtils.computeTotalPrice(skuDTOList);

        REcShoppingcart shoppingcart = new REcShoppingcart();
        shoppingcart.setNuserid(userId);
        shoppingcart.setScurrency(currency);
        shoppingcart.setSupdatetime(new Date());
        shoppingcart.setNtotalquantity(quantity);
        shoppingcart.setNdiscount(discount);
        shoppingcart.setNtotalprice(totalPrice);

        int insertResult = shoppingcartMapper.insert(shoppingcart);
        if (insertResult != 1){
            throw new Exception();
        }
        return shoppingcart;
    }

    private List<REcSku> createCartSKU(int userId, List<SKUDTO> skuDTOList) throws Exception {
        List<REcCartsku> cartskuList = new ArrayList<>();
        for (SKUDTO skuDTO : skuDTOList) {
            REcCartsku cartsku = new REcCartsku();
            cartsku.setNuserid(userId);
            cartsku.setNproductid(skuDTO.getSkuId());
            cartsku.setNquantity(skuDTO.getQuantity());
            int result = cartskuMapper.insert(cartsku);
            if (result != 1) {
                throw new Exception();
            }
            cartskuList.add(cartsku);
        }
        return getSKUListBycarskuList(cartskuList);
    }

    @Override
    public void addShoppCartAndSKUToRedis(ShoppingCartDTO returnShoppingCartDTO) throws Exception{
        getJedis().set((returnShoppingCartDTO.getUserId() + "").getBytes(), SerializeUtil.serialize(returnShoppingCartDTO));
    }

    /*********************************************search******************************************/
    /**
     * get shopping cart
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public REcShoppingcart getShoppingCart(int userId) throws Exception{
        REcShoppingcart shoppingcart = shoppingcartMapper.selectByPrimaryKey(userId);
        if (shoppingcart == null) {
            throw new Exception();
        }
        return shoppingcart;
    }

    /**
     * get skuList by userId
     * @param userId
     * @return
     */
    @Override
    public List<REcSku> getSKUList(int userId) {
        List<REcCartsku> cartskuList = cartskuMapper.selectByUserId(userId);
        List<REcSku> skuList = getSKUListBycarskuList(cartskuList);
//        List<REcSku> skuList = new ArrayList<>();
//        for (REcCartsku cartsku : cartskuList) {
//            skuList.add(skuMapper.selectByPrimaryKey(cartsku.getNproductid()));
//        }
        return skuList;
    }

    @Override
    public ShoppingCartDTO getShoppingCartFromRedis(int userId) throws Exception{
        Jedis jedis = getJedis();
        byte[] shoppingCart = jedis.get((userId + "").getBytes());
        Object shoppingCartObject = SerializeUtil.unserialize(shoppingCart);
        if (shoppingCartObject == null)
            return null;
        ShoppingCartDTO shoppingCartDTO = null;
        if (shoppingCartObject instanceof ShoppingCartDTO){
            shoppingCartDTO = (ShoppingCartDTO) shoppingCartObject;
        } else{
            throw new Exception();
        }
        return shoppingCartDTO;
    }

    /*********************************************delete******************************************/

//    /**
//     * not change skuList's quantity
//     * @param userId
//     * @return
//     */
//    private List<REcSku> getSKUListOrigin(int userId) {
//        List<REcCartsku> cartskuList = cartskuMapper.selectByUserId(userId);
////        List<REcSku> skuList = getSKUListBycarskuList(cartskuList);
//        List<REcSku> skuList = new ArrayList<>();
//        for (REcCartsku cartsku : cartskuList) {
//            skuList.add(skuMapper.selectByPrimaryKey(cartsku.getNproductid()));
//        }
//        return skuList;
//    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public void deleteShoppingCartAndSKU(int userId) throws Exception {
        List<REcCartsku> cartskuList = cartskuMapper.selectByUserId(userId);
        List<Integer> skuIdList = new ArrayList<>();
        cartskuList.forEach(n -> skuIdList.add(n.getNproductid()));
        for (Integer skuId : skuIdList) {
            deleteCartSKU(userId, skuId);
        }
        deleteShoppingCart(userId);
    }

    private boolean deleteShoppingCart(int userId) throws Exception{
        int deleteResult = shoppingcartMapper.deleteByPrimaryKey(userId);
        if (deleteResult != 1) {
            throw new Exception();
        }
        return true;
    }

    private boolean deleteCartSKU(int userId, int skuId) throws Exception {
        REcCartskuKey cartskuKey = new REcCartskuKey();
        cartskuKey.setNuserid(userId);
        cartskuKey.setNproductid(skuId);

        int result = cartskuMapper.deleteByPrimaryKey(cartskuKey);
        if (result != 1) {
            throw new Exception();
        }
        return true;
    }

    @Override
    public void deleteShoppingCartAndSKUToRedis(int userId) throws Exception {
        getJedis().del(userId + "");
    }


    /*********************************************udpate******************************************/
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public ShoppingCartDTO updateShoppingCartAndSKU(int type, ShoppingCartDTO shoppingCartDTO) throws Exception{

        if (type == 1) {//add
            if (!checkSKUParam(shoppingCartDTO.getSkudtoList(), getSKUListByDTOList(shoppingCartDTO.getSkudtoList()))) {
                return null;
            }
            REcShoppingcart shoppingcart = updateShoppingCart(type, shoppingCartDTO.getUserId(), shoppingCartDTO.getSkudtoList());
            List<REcSku> skuList = updateAddCartSKU(shoppingCartDTO.getUserId(), shoppingCartDTO.getSkudtoList());
            shoppingCartDTO = ConvertToDTO.convertToShoppingDTO(shoppingcart, skuList);
        } else if (type == 2) {//update
            if (!checkSKUParam(shoppingCartDTO.getSkudtoList(), getSKUListByDTOList(shoppingCartDTO.getSkudtoList()))) {
                return null;
            }
            REcShoppingcart shoppingcart = updateShoppingCart(type, shoppingCartDTO.getUserId(), shoppingCartDTO.getSkudtoList());
            List<REcSku> skuList = updateRefreshCartSKU(shoppingCartDTO.getUserId(), shoppingCartDTO.getSkudtoList());
            shoppingCartDTO = ConvertToDTO.convertToShoppingDTO(shoppingcart, skuList);
        } else if (type == 3) {//delete
            List<SKUDTO> skudtoList = shoppingCartDTO.getSkudtoList();
            List<REcSku> skuListCheck = getSKUListByDTOList(skudtoList);
            if (skudtoList.size() != skuListCheck.size())
                return null;
            Collections.sort(skudtoList, new SortList<SKUDTO>("SkuId",true));
            for (int i = 0; i < skudtoList.size(); i++) {
                if (skudtoList.get(i).getSkuId() != skuListCheck.get(i).getNskuid()) {
                    return null;
                }
            }
            REcShoppingcart shoppingcart = updateShoppingCart(type, shoppingCartDTO.getUserId(), shoppingCartDTO.getSkudtoList());
            List<REcSku> skuList = updateDeleteCartSKU(shoppingCartDTO.getUserId(), shoppingCartDTO.getSkudtoList());
            shoppingCartDTO = ConvertToDTO.convertToShoppingDTO(shoppingcart, skuList);
        } else {
            return null;
        }

        return shoppingCartDTO;
    }

    private REcShoppingcart updateShoppingCart(int type, int userId, List<SKUDTO> skuDTOList) throws Exception{
        int quantity = ComputeUtils.computeNumber(skuDTOList);
        float discount = ComputeUtils.computeDiscount(skuDTOList);
        BigDecimal totalPrice = ComputeUtils.computeTotalPrice(skuDTOList);

        REcShoppingcart shoppingcart = shoppingcartMapper.selectByPrimaryKey(userId);
        if (type == 1) {//add
            shoppingcart.setNtotalquantity(shoppingcart.getNtotalquantity() + quantity);
            shoppingcart.setNdiscount(shoppingcart.getNdiscount() + discount);
            shoppingcart.setNtotalprice(shoppingcart.getNtotalprice().add(totalPrice));
        } else if (type == 2) {//update
            computeQuantityDiscountTotalPrice(userId, skuDTOList, shoppingcart);
//            shoppingcart.setNtotalquantity(quantity);
//            shoppingcart.setNdiscount(discount);
//            shoppingcart.setNtotalprice(totalPrice);
        } else if (type == 3) {//delete
            shoppingcart.setNtotalquantity(shoppingcart.getNtotalquantity() - quantity);
            shoppingcart.setNdiscount(shoppingcart.getNdiscount() - discount);
            shoppingcart.setNtotalprice(shoppingcart.getNtotalprice().subtract(totalPrice));
        }

        int updateResult = shoppingcartMapper.updateByPrimaryKeySelective(shoppingcart);
        if (updateResult != 1) {
            throw new Exception();
        }
        REcShoppingcart shoppingcartResult = shoppingcartMapper.selectByPrimaryKey(userId);
        if (shoppingcartResult == null) {
            throw new Exception();
        }
        return shoppingcartResult;
    }

    private List<REcSku> updateAddCartSKU(int userId, List<SKUDTO> skuDTOList) throws Exception {
        for (SKUDTO skuDTO : skuDTOList) {
            REcCartsku cartsku = new REcCartsku();
            cartsku.setNuserid(userId);
            cartsku.setNproductid(skuDTO.getSkuId());
            cartsku.setNquantity(skuDTO.getQuantity());
            int result = cartskuMapper.insert(cartsku);
            if (result != 1) {
                throw new Exception();
            }
        }
        List<REcCartsku> cartskuList = cartskuMapper.selectByUserId(userId);
        List<REcSku> skuList = getSKUListBycarskuList(cartskuList);
        return skuList;
    }

    private List<REcSku> updateRefreshCartSKU(int userId, List<SKUDTO> skuDTOList) throws Exception {
        for (SKUDTO skuDTO : skuDTOList) {
            REcCartskuKey key = new REcCartskuKey();
            key.setNuserid(userId);
            key.setNproductid(skuDTO.getSkuId());
            REcCartsku cartsku = cartskuMapper.selectByPrimaryKey(key);
            cartsku.setNquantity(skuDTO.getQuantity());//update quantity
            int result = cartskuMapper.updateByPrimaryKey(cartsku);
            if (result != 1) {
                throw new Exception();
            }
        }
        List<REcCartsku> cartskuList = cartskuMapper.selectByUserId(userId);
        List<REcSku> skuList = getSKUListBycarskuList(cartskuList);
        return skuList;
    }

    private List<REcSku> updateDeleteCartSKU(int userId, List<SKUDTO> skuDTOList) throws Exception {
        for (SKUDTO skuDTO : skuDTOList) {
            REcCartskuKey key = new REcCartskuKey();
            key.setNuserid(userId);
            key.setNproductid(skuDTO.getSkuId());
            REcCartsku cartsku = cartskuMapper.selectByPrimaryKey(key);
            cartsku.setNquantity(skuDTO.getQuantity());//delete quantity
            int result = cartskuMapper.deleteByPrimaryKey(cartsku);
            if (result != 1) {
                throw new Exception();
            }
        }
        List<REcCartsku> cartskuList = cartskuMapper.selectByUserId(userId);
        List<REcSku> skuList = getSKUListBycarskuList(cartskuList);
        return skuList;
    }

    @Override
    public void updateShoppingCartAndSKUToRedis(ShoppingCartDTO returnShoppingCartDTO) throws Exception {
        getJedis().set((returnShoppingCartDTO.getUserId() + "").getBytes(), SerializeUtil.serialize(returnShoppingCartDTO));
    }

    /*********************************************check******************************************/
    /**
     * check sku param include price, quantity, id
     * @param skudtoList
     * @param skuList
     * @return
     */
    @Override
    public boolean checkSKUParam(List<SKUDTO> skudtoList, List<REcSku> skuList) {
        if (skudtoList.size() != skuList.size())
            return false;
        Collections.sort(skudtoList, new SortList<SKUDTO>("SkuId",true));
        for (int i = 0; i < skudtoList.size(); i++) {
            if (skudtoList.get(i).getSkuId() != skuList.get(i).getNskuid()
                    || skudtoList.get(i).getQuantity() > skuList.get(i).getNinventory() || skudtoList.get(i).getQuantity() <= 0
                    || skudtoList.get(i).getDisplayPrice().compareTo(skuList.get(i).getNdisplayPrice()) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * get skuList by dtoList
     * @param skudtoList
     * @return
     */
    @Override
    public List<REcSku> getSKUListByDTOList(List<SKUDTO> skudtoList) {
        List<Integer> skuIdList = new ArrayList<>();
        skudtoList.forEach(n -> skuIdList.add(n.getSkuId()));
        List<REcSku> skuList = skuMapper.getSKUList(skuIdList);
        return skuList;
    }

    /**
     * get skuList by cartskuList
     * @param cartskuList
     * @return
     */
    @Override
    public List<REcSku> getSKUListBycarskuList(List<REcCartsku> cartskuList) {
        List<Integer> skuIdList = new ArrayList<>();
        List<Integer> quantityList = new ArrayList<>();
        cartskuList.forEach(n -> skuIdList.add(n.getNproductid()));
        cartskuList.forEach(n -> quantityList.add(n.getNquantity()));
        List<REcSku> skuList = skuMapper.getSKUList(skuIdList);
        //inventory is sku's quantity to give dto to show
        for (int i = 0; i < skuList.size(); i++) {
            skuList.get(i).setNinventory(quantityList.get(i));
        }
        return skuList;
    }

    private REcShoppingcart computeQuantityDiscountTotalPrice(int userId, List<SKUDTO> skuDTOList, REcShoppingcart shoppingcart) {
        int quantityDTO = ComputeUtils.computeNumber(skuDTOList);
        float discountDTO = ComputeUtils.computeDiscount(skuDTOList);
        BigDecimal totalPriceDTO = ComputeUtils.computeTotalPrice(skuDTOList);

        int quantityOriginSKU = 0;
        float discountOriginSKU = 0;
        BigDecimal totalPriceOriginSKU = new BigDecimal(0);
        for (SKUDTO skudto : skuDTOList) {
            REcCartskuKey key = new REcCartskuKey();
            key.setNuserid(userId);
            key.setNproductid(skudto.getSkuId());
            REcCartsku cartsku = cartskuMapper.selectByPrimaryKey(key);
            REcSku sku = skuMapper.selectByPrimaryKey(skudto.getSkuId());
            quantityOriginSKU += cartsku.getNquantity();
            discountOriginSKU += cartsku.getNquantity() * sku.getNdiscount();

            BigDecimal displayPriceBig = sku.getNdisplayPrice();
            BigDecimal quantityBig = new BigDecimal(cartsku.getNquantity());
            BigDecimal price = displayPriceBig.multiply(quantityBig);
            totalPriceOriginSKU = totalPriceOriginSKU.add(price);
        }

        int quantity = shoppingcart.getNtotalquantity();
        float discount = shoppingcart.getNdiscount();
        BigDecimal totalPrice = shoppingcart.getNtotalprice();

        shoppingcart.setNtotalquantity(quantity - quantityOriginSKU + quantityDTO);
        shoppingcart.setNdiscount(discount - discountOriginSKU + discountDTO);
        shoppingcart.setNtotalprice(totalPrice.subtract(totalPriceOriginSKU).add(totalPriceDTO));

        return shoppingcart;
    }


}
