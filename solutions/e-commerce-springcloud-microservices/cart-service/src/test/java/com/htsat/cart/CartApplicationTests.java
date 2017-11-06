//package com.htsat.cart;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.htsat.cart.dto.SKUDTO;
//import com.htsat.cart.dto.ShoppingCartDTO;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.math.BigDecimal;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CartApplicationTests {
//
//	@Test
//	public void contextLoads() {
//	}
//
//	@Test
//	public void getShoppingCartTest() {
//
//	}
//
//	@Test
//	public void createShoppingCartTest() {
//		try {
//			sendCreateShoppingCartPost("http://localhost:8001/carts");
//		} catch (Exception e) {
//
//		}
//	}
//
//	private static String sendCreateShoppingCartPost(String url) throws IOException {
//		ShoppingCartDTO shoppingCart = new ShoppingCartDTO();
//		shoppingCart.setUserId(1L);
//		shoppingCart.setCurrency("RMB");
//		SKUDTO sku1 = new SKUDTO();
//		sku1.setSkuId(10001L);
//		sku1.setDiscount(new BigDecimal(99));
//		sku1.setDisplayPrice(new BigDecimal(700));
//		sku1.setQuantity(5);
//		SKUDTO sku2 = new SKUDTO();
//		sku2.setSkuId(10002L);
//		sku2.setDisplayPrice(new BigDecimal(799));
//		sku2.setQuantity(8);
//
//		List<SKUDTO> skuList = new ArrayList<>();
//		skuList.add(sku1);
//		skuList.add(sku2);
//		shoppingCart.setSkudtoList(skuList);
//
//		DefaultHttpClient httpClient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost(url);
//		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
//		String jsonstr = JSON.toJSONString(shoppingCart);
//		StringEntity se = new StringEntity(jsonstr);
//		se.setContentType("text/json");
//		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//		httpPost.setEntity(se);
//		HttpResponse response = httpClient.execute(httpPost);
//		//输出调用结果
//		if (response != null && response.getStatusLine().getStatusCode() == 200) {
//			String result = EntityUtils.toString(response.getEntity());
//		// 生成 JSON 对象
//			JSONObject obj = JSONObject.parseObject(result);
//			String errorcode = obj.getString("errorcode");
//			if ("000".equals(errorcode)) {
//				System.out.println("addHkfishOrder_request_success");
//			}
//		}
//		return "";
//	}
//
//	@Test
//	public void testDelete() throws IOException {
//
//		URL url = new URL("http://localhost:8001/carts?userId=1");
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setRequestMethod("DELETE");
//		connection.setDoInput(true);
//		connection.setDoOutput(true);
////		connection.setRequestProperty("name", "robben");
//		connection.setRequestProperty("content-type", "text/html");
//		OutputStreamWriter out = new OutputStreamWriter(
//				connection.getOutputStream(), "8859_1");
//		// 将要传递的集合转换成JSON格式
//		// 组织要传递的参数
//		out.flush();
//		out.close();
//		// 获取返回的数据
//		BufferedReader in = new BufferedReader(new InputStreamReader(
//				connection.getInputStream()));
//		String line = null;
//		StringBuffer content = new StringBuffer();
//		while ((line = in.readLine()) != null) {
//			// line 为返回值，这就可以判断是否成功
//			content.append(line);
//		}
//		in.close();
//	}
//
//	@Test
//	public void updateShoppingCartTest() {
//		try {
//			sendUpdateShoppingCartPost("http://localhost:8001/carts/3");
//		} catch (Exception e) {
//
//		}
//	}
//
//	private static String sendUpdateShoppingCartPost(String url) throws IOException {
//		ShoppingCartDTO shoppingCart = new ShoppingCartDTO();
//		shoppingCart.setUserId(1L);
//
//		SKUDTO sku1 = new SKUDTO();
//		sku1.setSkuId(10003L);
//		sku1.setDisplayPrice(new BigDecimal(799));
//		sku1.setDiscount(new BigDecimal(0));
//		sku1.setQuantity(10);
//
//
////		SKUDTO sku2 = new SKUDTO();
////		sku2.setSkuId(10002);
////		sku2.setDisplayPrice(new BigDecimal(799));
////		sku2.setQuantity(6);
//
//		List<SKUDTO> skuList = new ArrayList<>();
//		skuList.add(sku1);
////		skuList.add(sku2);
//		shoppingCart.setSkudtoList(skuList);
//
//		DefaultHttpClient httpClient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost(url);
//		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
//		String jsonstr = JSON.toJSONString(shoppingCart);
//		StringEntity se = new StringEntity(jsonstr);
//		se.setContentType("text/json");
//		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//		httpPost.setEntity(se);
//		HttpResponse response = httpClient.execute(httpPost);
//		//输出调用结果
//		if (response != null && response.getStatusLine().getStatusCode() == 200) {
//			String result = EntityUtils.toString(response.getEntity());
//			// 生成 JSON 对象
//			JSONObject obj = JSONObject.parseObject(result);
//			String errorcode = obj.getString("errorcode");
//			if ("000".equals(errorcode)) {
//				System.out.println("addHkfishOrder_request_success");
//			}
//		}
//		return "";
//	}
//
//}
