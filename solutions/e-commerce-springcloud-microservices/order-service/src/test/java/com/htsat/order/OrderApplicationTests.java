//package com.htsat.order;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.htsat.order.dto.AddressDTO;
//import com.htsat.order.dto.DeliveryDTO;
//import com.htsat.order.dto.OrderDTO;
//import com.htsat.order.dto.OrderSKUDTO;
//import com.htsat.order.enums.DeliveryStatusEnum;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
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
//public class OrderApplicationTests {
//
//	@Test
//	public void contextLoads() {
//	}
//
//	@Test
//	public void createShoppingCartTest() {
//		try {
//			sendCreateShoppingCartPost("http://localhost:8000/");
//		} catch (Exception e) {
//
//		}
//	}
//
//	private static String sendCreateShoppingCartPost(String url) throws IOException {
//
//		AddressDTO addressDTO = new AddressDTO();
//		addressDTO.setNaddressid(101L);
//
//		DeliveryDTO deliveryDTO = new DeliveryDTO();
//		deliveryDTO.setSexpresscompany("EMS");
//		deliveryDTO.setNdeliveryprice(new BigDecimal(500));
//		deliveryDTO.setNaddressid(addressDTO.getNaddressid());
//
//		List<OrderSKUDTO> orderskuDTOList = new ArrayList<>();
//		OrderSKUDTO ordersku = new OrderSKUDTO();
//		ordersku.setSkuId(10001L);
//		ordersku.setQuantity(10);
//		ordersku.setPrice(new BigDecimal(700));
//		ordersku.setOriginPrice(new BigDecimal(799));
//		ordersku.setDiscount(new BigDecimal(99));
//
//		OrderSKUDTO ordersku2 = new OrderSKUDTO();
//		ordersku2.setSkuId(10002L);
//		ordersku2.setQuantity(10);
//		ordersku2.setPrice(new BigDecimal(799));
//		ordersku2.setOriginPrice(new BigDecimal(799));
//		ordersku2.setDiscount(new BigDecimal(0));
//
//		orderskuDTOList.add(ordersku);
//		orderskuDTOList.add(ordersku2);
//
//		OrderDTO orderDTO = new OrderDTO();
//		orderDTO.setUserId(1L);
//		orderDTO.setAddressDTO(addressDTO);
//		orderDTO.setScustomermark("everything is good!");
//		orderDTO.setDeliveryDTO(deliveryDTO);
//		orderDTO.setOrderskudtoList(orderskuDTOList);
//
//		DefaultHttpClient httpClient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost(url);
//		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
//		String jsonstr = JSON.toJSONString(orderDTO);
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
//	@Test
//	public void testDelete() throws IOException {
//
//		URL url = new URL("http://localhost:8000/1/25");
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
////	@Test
////	public void updateOrderTest() {
////		try {
////			sendUpdateOrderPost("http://localhost:8000/orders/delivery?userId=1&orderId=4&deliveryStatus=1");
////		} catch (Exception e) {
////
////		}
////	}
////
////	@Test
////	public void updateOrderPaymentTest() {
////		try {
////			sendUpdateOrderPost("http://localhost:8000/orders/payment?userId=1&orderId=4&cardId=301&paymentPassword=123456");
////		} catch (Exception e) {
////
////		}
////	}
////
////	private static String sendUpdateOrderPost(String url) throws IOException {
////		DefaultHttpClient httpClient = new DefaultHttpClient();
////		HttpPost httpPost = new HttpPost(url);
////		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
////		String jsonstr = JSON.toJSONString(null);
////		StringEntity se = new StringEntity(jsonstr);
////		se.setContentType("text/json");
////		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
////		httpPost.setEntity(se);
////		HttpResponse response = httpClient.execute(httpPost);
////		//输出调用结果
////		if (response != null && response.getStatusLine().getStatusCode() == 200) {
////			String result = EntityUtils.toString(response.getEntity());
////			// 生成 JSON 对象
////			JSONObject obj = JSONObject.parseObject(result);
////			String errorcode = obj.getString("errorcode");
////			if ("000".equals(errorcode)) {
////				System.out.println("addHkfishOrder_request_success");
////			}
////		}
////		return "";
////	}
////
////
////    @Test
////    public void createShoppingCartTestByCart() {
////        try {
////            sendCreateShoppingCartPostByCart("http://localhost:8000/ordersCart");
////        } catch (Exception e) {
////
////        }
////    }
////
////    private static String sendCreateShoppingCartPostByCart(String url) throws IOException {
////
////        AddressDTO addressDTO = new AddressDTO();
////        addressDTO.setNaddressid(101L);
////
////        DeliveryDTO deliveryDTO = new DeliveryDTO();
////        deliveryDTO.setSexpresscompany("EMS");
////        deliveryDTO.setNdeliveryprice(new BigDecimal(100));
////        deliveryDTO.setNaddressid(addressDTO.getNaddressid());
////
////        OrderDTO orderDTO = new OrderDTO();
////        orderDTO.setUserId(1L);
////        orderDTO.setAddressDTO(addressDTO);
////        orderDTO.setScustomermark("everything is good!");
////        orderDTO.setDeliveryDTO(deliveryDTO);
////
////        DefaultHttpClient httpClient = new DefaultHttpClient();
////        HttpPost httpPost = new HttpPost(url);
////        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
////        String jsonstr = JSON.toJSONString(orderDTO);
////        StringEntity se = new StringEntity(jsonstr);
////        se.setContentType("text/json");
////        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
////        httpPost.setEntity(se);
////        HttpResponse response = httpClient.execute(httpPost);
////        //输出调用结果
////        if (response != null && response.getStatusLine().getStatusCode() == 200) {
////            String result = EntityUtils.toString(response.getEntity());
////            // 生成 JSON 对象
////            JSONObject obj = JSONObject.parseObject(result);
////            String errorcode = obj.getString("errorcode");
////            if ("000".equals(errorcode)) {
////                System.out.println("addHkfishOrder_request_success");
////            }
////        }
////        return "";
////    }
//}
