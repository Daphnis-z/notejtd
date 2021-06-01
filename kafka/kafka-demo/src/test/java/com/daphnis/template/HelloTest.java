package com.daphnis.template;

import org.junit.Assert;
import org.junit.Test;

public class HelloTest {


  @Test
  public void testSayHi() {
  	Hello hello=new Hello();
  	Assert.assertEquals("Hello World !!",hello.sayHi());
  }
}
