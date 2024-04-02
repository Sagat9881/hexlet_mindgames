package ru.apzakharov.data_structure.abstract_structure;

import java.util.List;
import java.util.NoSuchElementException;

public interface Queue<E> {
    /**
     * <p>Возвращает элемент из головы очереди. Элемент не удаляется.</p>
     * Если очередь пуста, инициируется исключение NoSuchElementException.
     */
    E element() throws NoSuchElementException;

    /**
     * <p>Удаляет элемент из головы очереди, возвращая его.</p>
     * Инициирует исключение NoSuchElementException, если очередь пуста.
     */
    E remove();

    /**
     * <p>Возвращает элемент из головы очереди. </p>
     * Возвращает null, если очередь пуста. Элемент не удаляется.
     */
    E peek();

    /**
     * <p>Возвращает элемент из головы очереди и удаляет его. </p>
     * Возвращает null, если очередь пуста.
     */
    E poll();

    /**
     * <p>Пытается добавить оbj в очередь.</p>
     * Возвращает true, если оbj добавлен, и false в противном случае.
     */
    boolean offer(E obj);

    interface ListQueue<E> extends Queue<E>, List<E> {

    }
}
