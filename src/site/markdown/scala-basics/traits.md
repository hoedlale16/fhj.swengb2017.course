## Traits

_Traits_ are code chunks which contain code which is typically reused in more than one location. They can be seen as a mean to define an interface, and in fact they are comparable to _interfaces_ in Java for example.

Here is an example for a trait:

~~~
trait Foo
~~~

Clearly, this trait is not of much value. However, even such simple definitions can be useful, for example if you want to link concepts together:

~~~
trait Animal
class Dog extends Animal
class Cat extends Animal
~~~

Both classes _Dog_ and _Cat_ share a common fact together, they both extend _Animal_.

Traits are a good place to share common code:

~~~
class Representation

trait Drawable {

 def id : Int = 0
 
 def oldId : Int = {
    return 0;
 }  
  
 def draw() : Representation  

}

class Circle extends Drawable {

  def draw() : Representation = ...
  
}

class Rectangle extends Drawable {

  def draw() : Representation = ...
  
}
~~~

Here, _Circle_ and _Rectangle_ extend both _Drawable_. Both _Rectangle_ and _Circle_ inherit three methods, named _id_, _oldId_ and _draw_. 

If we look at the _id_ method, we see that it is fully defined in the _Drawable_ trait, meaning it has an implementation (which is, trivially, only consisting of an integer value and written in the shortest form possible). 

Have a look at the method _oldId_. This method does the exact same thing like _id_, it is just written in a much more verbose way, and uses superfluous syntax. But in essence, it also returns an integer value.
 
Since _draw_ is defined as an abstract method (this means that the implementation is not defined, only the return type is given) all classes which extend this trait have to implement the _draw_ method. 

This is a common usecase for traits.

With the definitions above, you gain something very valuable: you can create an _API_ which only knows about _Drawable_ traits, and not about the special forms of this concept which are implemented in _Circle_ or _Rectangle_. 

Like this one can design an API at a certain point in time, without knowing all possible implementations which are maybe written for some future requirement. This is a very important programming trick and should be well remembered.

Typically, _classes_ extend _traits_. But _traits_ are not confined to that. A _trait_ can also extend other _traits_. _Objects_ can also extend _traits_. It is even possible to extend more than one _trait_. 

See the examples below:

~~~
trait A
trait B
trait C

class D extends A with B with C
trait G extends A with C

object F extends G
~~~

Here we've learned something new, namely the _with_ keyword. Using _with_, one can extend more than one _trait_. But be cautious, you always have to use _extends_ and only after that the ... _with_ ... constructs.
