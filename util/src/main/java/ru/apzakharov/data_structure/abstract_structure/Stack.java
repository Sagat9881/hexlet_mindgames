package ru.apzakharov.data_structure.abstract_structure;

public interface Stack<E> {
    /**
     * добавляет элемент на верх стека
     */
    void push(E element);

    /**
     * Удаляет верхний элемент из стека и возвращает его
     * @return Верхний элемент стэка
     */
    E pop();

    /**
     * Возвращает верхний элемент стека, но не удаляет его из стека
     * @return Верхний элемент стэка
     */
    E peek();
}
