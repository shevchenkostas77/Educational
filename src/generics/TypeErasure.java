package generics;

import java.util.ArrayList;

public class TypeErasure {
    //Когда мы пишием, как в стрке 7-мь то, компилятор это все понимает;
    ArrayList<Integer> al = new ArrayList<>();
    /*
        Но Runtime вся информация о generic удаляется,
        т.е. информацию о generic знает компилятор для необходимых ему проверок;
        JVM видит написанную 7-ую строку следующим образом:
        ArrayList al1 = new ArrayList();
        т.е. она воспринимает ArrayList как список "Raw type"
        следовательно, когда пишется строка на подобии, как ниже
    */
    int i = al.get(0);
    /*
        происходит следующее:
        int i = (Integer)al.get(0);
        т.к. без кастинга "al.get(0);" "Raw type" возвращает Object
        ЭТОТ процес называется стиранием типов
        Это необходимый механизм для поддержки обратной совместимости;
    */

    class Info<T> {
        private T value;

        Info(T value) {
            this.value = value;
        }

        public T getInfo() {
            return value;
        }
    }
    //Когда мы пишем следующий код:
    public void abc(Info<String> info) {
        String s = info.getInfo();
    }
//
//    public void abc(Info<Integer> info) {
//        Integer i = info.getInfo();
//    }
    /*
        копилятор не позволяет нам это сделать Overloading, т.к. JVM видит по следующему
        public void abc(Info info)
        т.е. одинаково
    */

    //В ниже написанном коде компилятор выдаст ошибку
//    class Parant {
//        public void abc(Info<String> info) {
//            String s = info.getInfo();
//        }
//    }

//    class Child extends Parant {
//        public void abc(Info<Integer> info) {
//            Integer s = info.getInfo();
//        }
//    }
    /*
        1. При переопределении обычного метода 'abc (A a)' в параметре можем использовать только тип А.
        Если использовать его дочерний класс, или какой-либо другой класс, то получаем overloaded method.
        2. При переопределении метода с generic 'abc(Info<A> info)' в параметре можем использовать:
        - abc(Info<A> info) (тот же тип A);
        - abc(Info info) (Raw type, т.е. сразу не указывать generic вовсе);
        В любом другом случае компилятор воспринимает метод в дочернем классе как overloaded, а не overridden.
        А когда создается overloaded метод с другим дженериком в параметре, компилятор выдает ошибку, т.к.
        компилятор может различить эти методы, а JVM после type erasure уже не сможет.
    */

}