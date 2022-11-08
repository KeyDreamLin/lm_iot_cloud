package com.lm;


// 继承、父类构造函数 、 子类调用父类的构造函数
public class AnimalTest {
     public static void main(String[] args) {
		 Rabbit r = new Rabbit();
		 r.setName("兔子");
		 r.setEat("胡萝卜");
		 Tiger t = new Tiger("老虎","胡萝卜");
//		 t.setName("老虎");
//		 t.setEat("肉");

		 r.getInfo();
		 t.getInfo();

     }
}
// 动物类 - 公共类
class Animal{
	// 动物的名称
	private String name;
	// 动物吃的食物
	private String eat;

	public Animal() {
	}

	public Animal(String name) {
		this.name = name;
	}

	public Animal(String name, String eat) {
		this.name = name;
		this.eat = eat;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEat() {
		return eat;
	}
	public void setEat(String eat) {
		this.eat = eat;
	}

	// 获取动物的信息
	void getInfo() {
		System.out.println("我是一个"+name+"我会睡觉");
		System.out.println("我是一个"+name+"我喜欢吃"+eat);
	}
}
// 兔子类 继承了 动物类
class Rabbit extends Animal{
	Rabbit(){
		super();
	}

	Rabbit(String name, String eat){
		super(name,eat);
	}
}
// 老虎类 继承了 动物类
class Tiger extends Animal{
	Tiger(){}
	Tiger(String name, String eat){
		super(name,eat);
	}
}