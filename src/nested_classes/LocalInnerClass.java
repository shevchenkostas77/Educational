package nested_classes;

/*
1. В отличие от static nested classes и inner classes local inner classes не являются элементами
класса, они локальны для какого-то метода или блока кода в котором определены;
2. Local inner класс не может быть static;
3. Область видимости local inner класса - это блок, в котором он находится;
4. Local inner класс может обращаться даже к private элементам внешнего класса;
5. Local inner класс может обращаться к элементам блока, в котором он написан при условии, что они
   final или effectively final;
6. Local inner классы могут наследовать классы, имплементировать интерфейсы.
   Как static nested классы и inner классы local inner классы могут быть final (не иметь наследников),
   быть abstract (невозможно создать объекты этого класса, его необходимо будет
   наследовать, перезаписать его абстрактные методы, если они есть и лишь потом
   создавать объекты-потомки)
7. Для local inner класса нельзя указывать access modifier
*/

public class LocalInnerClass {
    public static void main(String[] args) {
        Math math = new Math();
        math.getResult();
        // Вывод:
        // Delimoe = 21
        // Delitel = 4
        // Chastnoe = 5
        // Ostatok = 1
        // getPrivateElOuterClass = 10
        // getFinalValueMethod = 10

        System.out.println();

        Math2 math2 = new Math2();
        math2.getResult(21,4);
        // Вывод:
        // Delimoe = 21
        // Delitel = 4
        // Chastnoe = 5
        // Ostatok = 1

        /*
        Local inner class находится в методе getResult() и не доступен за пределами этого метода.
        Следующий код не скомпилируется:
            1. Division division = new Division();
            2. Math.Division division = new Math.Division();
            3. Math.Division division = math.new Division();
        */
    }
}

class Math {  // внешний класс для local inner класса Division;
    private final int a = 10;

    /*
    Класс Division не виден даже в классе Math;
    Следующий код не скомпилируется:
    Division division = new Division();
    */
    public void getResult() {
        /*
        Доступ у класса Division есть к переменной блока - finalValue. Но доступ есть тогда,
        когда эта переменная обозначена "final" (поменять нигде не возможно), но даже без ключевого
        слова "final" доступ у local inner класса есть к переменной блока, т.к. effectively final.
        Effectively final переменная - это переменная, которая не обозначена как "final", но после
        инициализации которой, значение не меняется.
        */
        final int finalValue = 10; // final переменная блока;

        int effectivelyFinalValue = 20; // effectively final переменная блока;
        /*
        Если поменять значение effectively final переменной блока, как сделано это ниже,
        то уже внутри local inner класса использовать эту переменную не удастся.
        */
        effectivelyFinalValue = 50;

        class Division { // По аналогии с локальной переменной модификатор "static" здесь запрещен
            private int delimoe;
            private int delitel;

            public int getDelimoe() {
                return delimoe;
            }

            public void setDelimoe(int delimoe) {
                this.delimoe = delimoe;
            }

            public int getDelitel() {
                return delitel;
            }

            public void setDelitel(int delitel) {
                this.delitel = delitel;
            }

            public int getChastnoe() {
                return delimoe / delitel;
            }

            public int getOstatok() {
                return delimoe % delitel;
            }

            /*
            Метод getPrivateElOuterClass() показывает, что local inner класс Division может
            обращаться даже к private элементам внешнего класса.
            */
            public void getPrivateElOuterClass() {
                System.out.println("getPrivateElOuterClass = " + a);
            }

            /*
            Метод getFinalValueMethod() показывает, что local inner класс Division имеет доступ к
            переменной блока, но доступ есть тогда, когда эта переменная обозначена "final"
            (поменять нигде не возможно).
            */
            public void getFinalValueMethod() {
                System.out.println("getFinalValueMethod = " + finalValue);
            }

            public void getEffectivelyFinalValueMethod() {
                /*
                На 58 строке кода поменяли значение effectively final переменной блока,
                поэтому использовать ее в local inner классе не получится. Но если значение
                не было бы изменено на 58 строке кода, использовать данную переменную было бы
                возможно. То же самое будет, если изменить переменную effectivelyFinalValue в самом
                local inner классе, как на 109 строке кода.
                */
                // effectivelyFinalValue = 50;
                // System.out.println(effectivelyFinalValue);

            }
        }

        Division division = new Division();
        division.setDelimoe(21);
        division.setDelitel(4);
        System.out.println("Delimoe = " + division.getDelimoe());
        System.out.println("Delitel = " + division.getDelitel());
        System.out.println("Chastnoe = " + division.getChastnoe());
        System.out.println("Ostatok = " + division.getOstatok());
        division.getPrivateElOuterClass();
        division.getFinalValueMethod();
    }
}

class Math2 {
    public void getResult(final int delimoe, final int delitel) {
        /*
        Значения, которые задаются в параметрах метода при его вызове, менять нельзя, поэтому
        для корректной работы данного метода необходимо пометить каждый параметр ключевым
        словом "final", но можно и без этого, главное, чтобы значения не менялись (effectively final).
        */
        class Division {

            public int getChastnoe() {
                return delimoe / delitel;
            }

            public int getOstatok() {
                return delimoe % delitel;
            }
        }

        Division division = new Division();
        System.out.println("Delimoe = " + delimoe);
        System.out.println("Delitel = " + delitel);
        System.out.println("Chastnoe = " + division.getChastnoe());
        System.out.println("Ostatok = " + division.getOstatok());
    }
}

