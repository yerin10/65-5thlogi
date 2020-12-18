package com.estimulo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/* �궗�슜�옄�젙�쓽 annotation */
public @interface Dataset {
	
	/* 硫붿꽌�뱶 */
    public String name();
}

/* annotation�쓣 �겢�옒�뒪�뿉�쟻�슜�쓣 �븯怨� 諛붿씠�듃肄붾뱶�뙆�씪源뚯� annotation�젙蹂대�� �쑀吏��릺寃� �븳�떎.*/

/*
 * 	class �삉�뒗 field �뿉 �꽕�젙�맂 annotation 媛앹껜瑜� �궗�슜�븯湲� �쐞�빐�꽌�뒗
	@Retention(RetentionPolicy.RUNTIME) 瑜� �꽑�뼵�빐�빞�븳�떎.

	<�궗�슜���긽>
 * 	�궗�슜 ���긽
 	- class : @Target(ElementType.TYPE)
 	- field : @Target(ElementType.FIELD)

 	@Target 	- Constructor, Field, Enum, Local Variable, Method, Package, Parameter, Type(Class)
	@Retention 	- Source, Class, Runtime
	@Documented - annotation�쓣 javadoc�뿉 �룷�븿�븳�떎.
	@Inherited 	- annotation�쓣 �긽�냽�쓣 媛��뒫耳� �븳�떎.

 */