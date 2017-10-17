package com.htsat.cart.serviceimpl;

import com.htsat.cart.config.RedisConfig;
import com.htsat.cart.exception.DeleteException;
import com.htsat.cart.exception.InsertException;
import com.htsat.cart.exception.SearchException;
import com.htsat.cart.exception.UpdateException;
import com.htsat.cart.dao.REcCartskuMapper;
import com.htsat.cart.dao.REcShoppingcartMapper;
import com.htsat.cart.dao.REcSkuMapper;
import com.htsat.cart.dto.SKUDTO;
import com.htsat.cart.dto.ShoppingCartDTO;
import com.htsat.cart.model.REcCartsku;
import com.htsat.cart.model.REcCartskuKey;
import com.htsat.cart.model.REcShoppingcart;
import com.htsat.cart.model.REcSku;
//import com.htsat.cart.service.IRedisService;
import com.htsat.cart.service.IShoppingCartService;
import com.htsat.cart.utils.ComputeUtils;
import com.htsat.cart.utils.ConvertToDTO;
import com.htsat.cart.utils.SerializeUtil;
import com.htsat.cart.utils.SortList;
import org.apache.commons.lang.StringUtils;
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
    private RedisConfig redisConfig;

//    @Autowired
//    private IRedisService redisService;
//
//    private Jedis getJedis(){
//        return redisService.getResource();
//    }

    /******************************************create*********************************************/

    /**
     *
     * @param shoppingCartDTO
     * @throws InsertException
     */
    @Override
    public void addShoppingCartAndSKU(ShoppingCartDTO shoppingCartDTO) throws InsertException {
        ShoppingCartDTO returnShoppingCartDTO = addShoppingCartAndSKUToMySQL(shoppingCartDTO);
        addShoppCartAndSKUToRedis(returnShoppingCartDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=InsertException.class)
    public ShoppingCartDTO addShoppingCartAndSKUToMySQL(ShoppingCartDTO shoppingCartDTO) throws InsertException {
        // 创建购物车
        REcShoppingcart shoppingcart = createShoppingCart(shoppingCartDTO.getUserId(), shoppingCartDTO.getCurrency(), shoppingCartDTO.getSkudtoList());
        //添加商品
        List<REcSku> skuList = createCartSKU(shoppingCartDTO, shoppingCartDTO.getSkudtoList(), shoppingcart.getNshoppingcartid());
        //返回DTO数据
        ShoppingCartDTO returnShoppingCartDTO = ConvertToDTO.convertToShoppingDTO(shoppingcart, skuList);
        return returnShoppingCartDTO;
    }

    private REcShoppingcart createShoppingCart(Long userId, String currency, List<SKUDTO> skuDTOList) throws InsertException {
        int totalquantity = ComputeUtils.computeNumber(skuDTOList);
        BigDecimal totaldiscount = ComputeUtils.computeDiscount(skuDTOList);
        BigDecimal totalPrice = ComputeUtils.computeTotalPrice(skuDTOList);

        REcShoppingcart shoppingcart = new REcShoppingcart();
        shoppingcart.setNuserid(userId);
        shoppingcart.setScurrency(currency);
        shoppingcart.setNtotalquantity(totalquantity);
        shoppingcart.setNdiscount(totaldiscount);
        shoppingcart.setNtotalprice(totalPrice);

        int insertResult = shoppingcartMapper.insertSelective(shoppingcart);
        if (insertResult != 1){
            throw new InsertException("mysql : create ShoppingCart failed");
        }
        return shoppingcart;
    }

    private List<REcSku> createCartSKU(ShoppingCartDTO shoppingCartDTO, List<SKUDTO> skuDTOList, Long shoppingcartId) throws InsertException {
        List<REcCartsku> cartskuList = new ArrayList<>();
        for (SKUDTO skuDTO : skuDTOList) {
            REcCartsku cartsku = new REcCartsku();
            cartsku.setNuserid(shoppingCartDTO.getUserId());
            cartsku.setNshoppingcartid(shoppingcartId);
            cartsku.setNskuid(skuDTO.getSkuId());
            cartsku.setNquantity(skuDTO.getQuantity());
            int result = cartskuMapper.insertSelective(cartsku);
            if (result != 1) {
                throw new InsertException("mysql : create SKU failed");
            }
            cartskuList.add(cartsku);
        }
        return getSKUListBycarskuList(cartskuList);
    }

    private void addShoppCartAndSKUToRedis(ShoppingCartDTO returnShoppingCartDTO) throws InsertException{
        Jedis jedis = redisConfig.getJedis();
        String code = null;
        try {
            code = jedis.set((returnShoppingCartDTO.getUserId() + "").getBytes(), SerializeUtil.serialize(returnShoppingCartDTO));
        } catch (Exception e) {
            logger.error("Redis insert error: "+ e.getMessage() +" - " + returnShoppingCartDTO.getUserId() + ", value:" + returnShoppingCartDTO);
        } finally{
            redisConfig.returnResource(jedis);
        }
        if (StringUtils.isEmpty(code)) {
            throw new InsertException("redis : create failed");
        }
    }

    /*********************************************search******************************************/
    /**
     *
     * @param userId
     * @return
     * @throws SearchException
     */
    @Override
    public ShoppingCartDTO getShoppingCart(Long userId) throws SearchException{

        ShoppingCartDTO shoppingCartDTO = getShoppingCartFromRedis(userId);

        if (shoppingCartDTO == null) {
            REcShoppingcart shoppingcart = getShoppingCartByMySQL(userId);
            List<REcSku> skuList = getSKUListByMySQL(shoppingcart.getNshoppingcartid());
            shoppingCartDTO = ConvertToDTO.convertToShoppingDTO(shoppingcart, skuList);
        }

        return shoppingCartDTO;
    }

    private REcShoppingcart getShoppingCartByMySQL(Long userId) throws SearchException{
        List<REcShoppingcart> shoppingcartList = shoppingcartMapper.selectByUserId(userId);
        if (shoppingcartList == null || shoppingcartList.size() != 1) {
            throw new SearchException("mysql : search ShoppingCart failed");
        }
        return shoppingcartList.get(0);
    }

    private List<REcSku> getSKUListByMySQL(Long shoppingcartId) throws SearchException{
        List<REcCartsku> cartskuList = cartskuMapper.selectByShoppingCartId(shoppingcartId);
        List<REcSku> skuList = getSKUListBycarskuList(cartskuList);
        if (skuList == null) {
            throw new SearchException("mysql : search SKU failed");
        }
        return skuList;
    }

    private ShoppingCartDTO getShoppingCartFromRedis(Long userId) throws SearchException{
        Jedis jedis = redisConfig.getJedis();
        if(jedis == null || !jedis.exists(userId + "")){
            return null;
        }
        Object shoppingCartObject = null;
        try {
            byte[] shoppingCart = jedis.get((userId + "").getBytes());
            shoppingCartObject = SerializeUtil.unserialize(shoppingCart);
        } catch (Exception e) {
            logger.error("Redis get error: "+ e.getMessage() +" - key : " + userId);
        } finally{
            redisConfig.returnResource(jedis);
        }

        if (shoppingCartObject == null)
            return null;
        ShoppingCartDTO shoppingCartDTO = null;
        if (shoppingCartObject instanceof ShoppingCartDTO){
            shoppingCartDTO = (ShoppingCartDTO) shoppingCartObject;
        } else{
            throw new SearchException("redis : search failed");
        }
        return shoppingCartDTO;
    }

    /*********************************************delete******************************************/

    @Override
    public void deleteShoppingCartAndSKU(Long userId) throws DeleteException {
        REcShoppingcart shoppingcart = getShoppingCartByUserId(userId);
        deleteShoppingCartAndSKUByMySQL(shoppingcart.getNshoppingcartid());
        deleteShoppingCartAndSKUToRedis(shoppingcart.getNshoppingcartid());
    }


    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public void deleteShoppingCartAndSKUByMySQL(Long shoppingcartId) throws DeleteException {
        List<REcCartsku> cartskuList = cartskuMapper.selectByShoppingCartId(shoppingcartId);
        List<Long> skuIdList = new ArrayList<>();
        cartskuList.forEach(n -> skuIdList.add(n.getNskuid()));
        for (Long skuId : skuIdList) {
            deleteCartSKU(shoppingcartId, skuId);
        }
        deleteShoppingCart(shoppingcartId);
    }

    private boolean deleteShoppingCart(Long shoppingcartId) throws DeleteException{
        int deleteResult = shoppingcartMapper.deleteByPrimaryKey(shoppingcartId);
        if (deleteResult != 1) {
            throw new DeleteException("mysql : delete ShoppingCart failed");
        }
        return true;
    }

    private boolean deleteCartSKU(Long shoppingcartId, Long skuId) throws DeleteException {
        REcCartskuKey cartskuKey = new REcCartskuKey();
        cartskuKey.setNshoppingcartid(shoppingcartId);
        cartskuKey.setNskuid(skuId);

        int result = cartskuMapper.deleteByREcCartskuKey(cartskuKey);
        if (result != 1) {
            throw new DeleteException("mysql : delete CartSKU failed");
        }
        return true;
    }

    private void deleteShoppingCartAndSKUToRedis(Long userId) throws DeleteException {
        Jedis jedis = redisConfig.getJedis();
        try {
            Long reply = jedis.del(userId + "");
        } catch (Exception e) {
            logger.error("Redis delete error: "+ e.getMessage() +" - key : " + userId);
        }finally{
            redisConfig.returnResource(jedis);
        }
        if (false) {
            throw new DeleteException("redis : delete failed");
        }
    }


    /*********************************************udpate******************************************/

    @Override
    public ShoppingCartDTO updateShoppingCartAndSKU(int type, ShoppingCartDTO shoppingCartDTO) throws UpdateException {
        ShoppingCartDTO returnShoppingCartDTO = updateShoppingCartAndSKUByMySQL(type, shoppingCartDTO);
        updateShoppingCartAndSKUToRedis(returnShoppingCartDTO);
        return returnShoppingCartDTO;
    }


    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=3600,rollbackFor=Exception.class)
    public ShoppingCartDTO updateShoppingCartAndSKUByMySQL(int type, ShoppingCartDTO shoppingCartDTO) throws UpdateException{

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
                if (!skudtoList.get(i).getSkuId().equals(skuListCheck.get(i).getNskuid())) {
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

    private REcShoppingcart updateShoppingCart(int type, Long userId, List<SKUDTO> skuDTOList) throws UpdateException{
        REcShoppingcart shoppingcart = getShoppingCartByUserId(userId);
        int quantity = ComputeUtils.computeNumber(skuDTOList);
        BigDecimal discount = ComputeUtils.computeDiscount(skuDTOList);
        BigDecimal totalPrice = ComputeUtils.computeTotalPrice(skuDTOList);

//        REcShoppingcart shoppingcart = shoppingcartMapper.selectByPrimaryKey(userId);
        if (type == 1) {//add
            shoppingcart.setNtotalquantity(shoppingcart.getNtotalquantity() + quantity);
            shoppingcart.setNdiscount(shoppingcart.getNdiscount().add(discount));
            shoppingcart.setNtotalprice(shoppingcart.getNtotalprice().add(totalPrice));
        } else if (type == 2) {//update
            computeQuantityDiscountTotalPrice(skuDTOList, shoppingcart);
        } else if (type == 3) {//delete
            shoppingcart.setNtotalquantity(shoppingcart.getNtotalquantity() - quantity);
            shoppingcart.setNdiscount(shoppingcart.getNdiscount().subtract(discount));
            shoppingcart.setNtotalprice(shoppingcart.getNtotalprice().subtract(totalPrice));
        }

        int updateResult = shoppingcartMapper.updateByPrimaryKeySelective(shoppingcart);
        if (updateResult != 1) {
            throw new UpdateException("mysql : update ShoppingCart failed");
        }
        REcShoppingcart shoppingcartResult = shoppingcartMapper.selectByPrimaryKey(shoppingcart.getNshoppingcartid());
        if (shoppingcartResult == null) {
            throw new UpdateException("mysql : update search ShoppingCart failed");
        }
        return shoppingcartResult;
    }

    private List<REcSku> updateAddCartSKU(Long userId, List<SKUDTO> skuDTOList) throws UpdateException {
        REcShoppingcart shoppingcartResult = getShoppingCartByUserId(userId);
        for (SKUDTO skuDTO : skuDTOList) {
            REcCartsku cartsku = new REcCartsku();
            cartsku.setNuserid(userId);
            cartsku.setNshoppingcartid(shoppingcartResult.getNshoppingcartid());
            cartsku.setNskuid(skuDTO.getSkuId());
            cartsku.setNquantity(skuDTO.getQuantity());
            int result = cartskuMapper.insert(cartsku);
            if (result != 1) {
                throw new UpdateException("mysql : update add CartSKU failed");
            }
        }
        List<REcCartsku> cartskuList = cartskuMapper.selectByShoppingCartId(shoppingcartResult.getNshoppingcartid());
        List<REcSku> skuList = getSKUListBycarskuList(cartskuList);
        return skuList;
    }

    private List<REcSku> updateRefreshCartSKU(Long userId, List<SKUDTO> skuDTOList) throws UpdateException {
        REcShoppingcart shoppingcart = getShoppingCartByUserId(userId);
        for (SKUDTO skuDTO : skuDTOList) {
            REcCartskuKey key = new REcCartskuKey();
            key.setNshoppingcartid(shoppingcart.getNshoppingcartid());
            key.setNskuid(skuDTO.getSkuId());
            REcCartsku cartsku = cartskuMapper.selectByREcCartskuKey(key);
            cartsku.setNquantity(skuDTO.getQuantity());//update quantity
            int result = cartskuMapper.updateByPrimaryKey(cartsku);
            if (result != 1) {
                throw new UpdateException("mysql : update refresh CartSKU failed");
            }
        }
        List<REcCartsku> cartskuList = cartskuMapper.selectByShoppingCartId(shoppingcart.getNshoppingcartid());
        List<REcSku> skuList = getSKUListBycarskuList(cartskuList);
        return skuList;
    }

    private List<REcSku> updateDeleteCartSKU(Long userId, List<SKUDTO> skuDTOList) throws UpdateException {
        REcShoppingcart shoppingcart = getShoppingCartByUserId(userId);
        for (SKUDTO skuDTO : skuDTOList) {
            REcCartskuKey key = new REcCartskuKey();
            key.setNshoppingcartid(shoppingcart.getNshoppingcartid());
            key.setNskuid(skuDTO.getSkuId());
            REcCartsku cartsku = cartskuMapper.selectByREcCartskuKey(key);
            cartsku.setNquantity(skuDTO.getQuantity());//delete quantity
            int result = cartskuMapper.deleteByPrimaryKey(cartsku.getNcartskuid());
            if (result != 1) {
                throw new UpdateException("mysql : update delete CartSKU failed");
            }
        }
        List<REcCartsku> cartskuList = cartskuMapper.selectByShoppingCartId(shoppingcart.getNshoppingcartid());
        List<REcSku> skuList = getSKUListBycarskuList(cartskuList);
        return skuList;
    }

    private void updateShoppingCartAndSKUToRedis(ShoppingCartDTO returnShoppingCartDTO) throws UpdateException {
        Jedis jedis = redisConfig.getJedis();
        String reply = null;
        try {
            reply = jedis.set((returnShoppingCartDTO.getUserId() + "").getBytes(), SerializeUtil.serialize(returnShoppingCartDTO));
        } catch (Exception e) {
            logger.error("Redis update error: "+ e.getMessage() +" - " + returnShoppingCartDTO.getUserId() + "" + ", value:" + returnShoppingCartDTO);
        }finally{
            redisConfig.returnResource(jedis);
        }

        if (StringUtils.isEmpty(reply)) {
            throw new UpdateException("redis : update failed");
        }
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
            if (!skudtoList.get(i).getSkuId().equals(skuList.get(i).getNskuid())
                    || skudtoList.get(i).getQuantity() > skuList.get(i).getNinventory() || skudtoList.get(i).getQuantity() <= 0
                    || skudtoList.get(i).getDisplayPrice().compareTo(skuList.get(i).getNdisplayprice()) != 0) {
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
        List<Long> skuIdList = new ArrayList<>();
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
        List<Long> skuIdList = new ArrayList<>();
        List<Integer> quantityList = new ArrayList<>();
        cartskuList.forEach(n -> skuIdList.add(n.getNskuid()));
        cartskuList.forEach(n -> quantityList.add(n.getNquantity()));
        List<REcSku> skuList = skuMapper.getSKUList(skuIdList);
        //inventory is sku's quantity to give dto to show
        for (int i = 0; i < skuList.size(); i++) {
            skuList.get(i).setNinventory(quantityList.get(i));
        }
        return skuList;
    }

    private REcShoppingcart computeQuantityDiscountTotalPrice(List<SKUDTO> skuDTOList, REcShoppingcart shoppingcart) {
        int quantityDTO = ComputeUtils.computeNumber(skuDTOList);
        BigDecimal discountDTO = ComputeUtils.computeDiscount(skuDTOList);
        BigDecimal totalPriceDTO = ComputeUtils.computeTotalPrice(skuDTOList);

        int quantityOriginSKU = 0;
        BigDecimal discountOriginSKU = new BigDecimal(0);
        BigDecimal totalPriceOriginSKU = new BigDecimal(0);
        for (SKUDTO skudto : skuDTOList) {
            REcCartskuKey key = new REcCartskuKey();
            key.setNshoppingcartid(shoppingcart.getNshoppingcartid());
            key.setNskuid(skudto.getSkuId());
            REcCartsku cartsku = cartskuMapper.selectByREcCartskuKey(key);
            if (cartsku == null)
                return null;
            REcSku sku = skuMapper.selectByPrimaryKey(skudto.getSkuId());
            if (sku == null)
                return null;
            quantityOriginSKU += cartsku.getNquantity();
            discountOriginSKU = discountOriginSKU.add(sku.getNdiscount().multiply(new BigDecimal(cartsku.getNquantity())));

            BigDecimal displayPriceBig = sku.getNdisplayprice();
            BigDecimal quantityBig = new BigDecimal(cartsku.getNquantity());
            BigDecimal price = displayPriceBig.multiply(quantityBig);
            totalPriceOriginSKU = totalPriceOriginSKU.add(price);
        }

        int quantity = shoppingcart.getNtotalquantity();
        BigDecimal discount = shoppingcart.getNdiscount();
        BigDecimal totalPrice = shoppingcart.getNtotalprice();

        shoppingcart.setNtotalquantity(quantity - quantityOriginSKU + quantityDTO);
        shoppingcart.setNdiscount(discount.subtract(discountOriginSKU).add(discountDTO));
        shoppingcart.setNtotalprice(totalPrice.subtract(totalPriceOriginSKU).add(totalPriceDTO));

        return shoppingcart;
    }

    private REcShoppingcart getShoppingCartByUserId(Long userId) {
        List<REcShoppingcart> shoppingcartList = shoppingcartMapper.selectByUserId(userId);
        if (shoppingcartList == null || shoppingcartList.size() != 1) {
            return null;
        }
        return shoppingcartList.get(0);
    }

}
