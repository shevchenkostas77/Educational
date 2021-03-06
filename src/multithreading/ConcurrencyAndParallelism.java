package multithreading;

/*
Concurrency (с англ. согласованность)
Concurrent (с англ. совпадающий, согласованный, а как же есть перевод параллельный)
Ниже два примера для рассмотрения этих терминов.

Задание №1:
Допустим, есть задание петь и кушать.

Задание №2:
Допустим, есть задание готовить и говорить по телефону.

У многопоточности основные цели всего две:
1. Производительность;
2. Concurrency;

Concurrency означает выполнение сразу нескольких задач, но не обязательно в одно и то же время. Как достигается
concurrency, зависит от процессора компьютера. Так в первом задании, в одно и то же мгновение можно только либо петь,
либо кушать. Одновременно и петь и кушать невозможно. Человек съел кусочек еды, поет, потом прерывается, съедает второй
кусочек еды, потом продолжает петь и тд. В этом примере все работает так, как происходит работа с несколькими потоками
на компьютере с одноядерным процессором (information is in the file "IntroToMultithreading"). Тут видимость
одновременной работы достигается за счет технологии Context switching. В примере №1, несмотря на то, что человек
не может петь и есть в один момент времени, все равно происходит выполнение нескольких задач сразу.
Во примере №2 человек может готовить и говорить по телефону в один момент времени (абсолютно одновременно).
Что пример №1, что пример №2 - это concurrency. Тут можно провести аналогию с многоядерным процессором (information
is in the file "IntroToMultithreading"). Два потока - готовка и разговор по телефону в одно и то же мгновенье,
тогда как, петь и кушать в одно и то же мгновение невозможно. В обоих случаях выполнение заданий происходит сразу,
только в первом примере человек чуть-чуть кушает, затем чуть-чуть поет, затем снова чуть-чуть кушает и тд., а
во втором примере готовка и разговор по телефону абсолютно одновременно происходит. В примере №2 concurrency
достигается с помощью parallelism. Т.е. concurrency и parallelism - это не одно и тоже, но и не являются эти термины
взаимоисключающими или антонимами. Parallelism означает выполнение каких либо действий, в данном случае потоков,
параллельно, в одно и то же время. Т.е. parallelism это частный случай concurrency.
Как итог вышенаписанного:

Concurrency означает выполнение сразу нескольких задач. В зависимости от процессора компьютера concurrency может
достигаться разными способами.

Parallelism означает выполнение 2-х и более задач в одно и то же время, т.е. параллельно. В компьютерах с многоядерным
процессором concurrency может достигаться за счет parallelism.
 */

public class ConcurrencyAndParallelism {
}
