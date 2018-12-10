package com.leafive.maybe.web.MqTest;

import com.leafive.maybe.web.model.Product;
import com.leafive.maybe.web.model.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author jj
 * @Date 2018/11/28 10:24
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class Java8Test {

	/**
	  * @Description:java8两个list赋值的问题场景
	  * @author JJ
	  * @param [] 
	  * @return void
	  * @date 2018/11/30 9:22 
	*/
	@Test
	public void testJava8(){
		List<User> lists1 = Lists.newArrayList();
		User user = new User();
		user.setId(1);
		user.setAge(12);
		lists1.add(user);
		User user1 = new User();
		user1.setId(2);
		user1.setAge(13);
		lists1.add(user1);

		List<Product> lists2 = Lists.newArrayList();
		Product product = new Product();
		product.setId(1L);
		product.setPrice(new BigDecimal(100));
		lists2.add(product);
		Map<Integer, User> map = lists1.stream().collect(Collectors.toMap(User::getId, User -> User));

		lists2.forEach(d->{
			if(map.containsKey(d.getId().intValue())){
				BigDecimal price = new BigDecimal(map.get(d.getId().intValue()).getAge());
				d.setPrice(price);
			}
		});

		System.out.println("=========="+lists2.toString());
	}
	/**
	 * @Description java8多条件过滤
	 * @Author JJ
	 * @Date 9:37 2018/11/30
	 * @param @param
	 * @return void
	 **/
	@Test
	public void testJava8For1(){
		List<User> lists1 = Lists.newArrayList();
		User user = new User();
		user.setId(1);
		user.setAge(12);
		lists1.add(user);
		User user1 = new User();
		user1.setId(2);
		user1.setAge(13);
		lists1.add(user1);

		List<User> collect = lists1.stream().filter(d->d.getId()>=1 && d.getAge()>12).collect(Collectors.toList());
		System.out.println(collect.toString());

	}
}
