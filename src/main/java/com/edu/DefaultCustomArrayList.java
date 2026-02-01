package com.edu;

import java.util.Arrays;
import java.util.Iterator;

public class DefaultCustomArrayList<E> implements CustomArrayList<E> {

    // Внутреннее хранилище: массив объектов
    private Object[] elements;
    // Количество реально добавленных элементов
    private int size;
    // Начальная емкость массива по умолчанию
    private static final int DEFAULT_CAPACITY = 10;

    public DefaultCustomArrayList() {
        // Создаем массив при старте
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public boolean add(E element) {
        // Если массив полон, увеличиваем его через Arrays.copyOf
        if (size == elements.length) {
            int newCapacity = elements.length + (elements.length / 2) + 1;
            elements = Arrays.copyOf(elements, newCapacity);
        }
        elements[size++] = element; // Добавляем элемент и увеличиваем счетчик
        return true;
    }

    @Override
    public boolean remove(E element) {
        for (int i = 0; i < size; i++) {
            // Ищем элемент через equals
            if (elements[i].equals(element)) {
                remove(i); // Если нашли, вызываем вспомогательный метод удаления по индексу
                return true;
            }
        }
        return false;
    }

    // Вспомогательный метод для сдвига элементов
    private void remove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            // Сдвигаем элементы влево, используя System.arraycopy
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null; // Зануляем последний элемент для очистки памяти
    }

    @Override
    public E get(int index) {
        // Проверка границ индекса
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) elements[index]; // Приведение типа к E
    }

    @Override
    public int size() {
        return size; // Возвращаем реальное количество элементов
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // Список пуст, если размер равен 0
    }

    @Override
    public void clear() {
        // Зануляем все ячейки
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        // Реализация итератора для цикла for-each
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                return (E) elements[currentIndex++];
            }
        };
    }
}
