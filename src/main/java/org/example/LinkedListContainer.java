package main.java.org.example;

/**
 * Реализация последовательной структуры данных на основе цепочки элементов.
 * Предоставляет основные операции управления элементами без использования
 * стандартных коллекций Java.
 *
 * <p>Цепочка элементов состоит из звеньев, где каждое звено содержит значение
 * и связь со следующим звеном. Реализация поддерживает ссылки на начальное
 * и конечное звенья для эффективного добавления элементов в конец цепочки.</p>
 *
 * @param <T> тип элементов в этой структуре
 */
public class LinkedListContainer<T> implements LinkedList<T> {
    private ChainElement<T> start;
    private ChainElement<T> end;
    private int elementCount;

    /**
     * Создает пустую структуру данных.
     *
     * <p>Устанавливает начальные значения:
     * <ul>
     *   <li>start = null (начало цепочки)</li>
     *   <li>end = null (конец цепочки)</li>
     *   <li>elementCount = 0 (количество элементов)</li>
     * </ul>
     * </p>
     */
    public LinkedListContainer() {
        this.start = null;
        this.end = null;
        this.elementCount = 0;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Реализация добавляет элемент в завершение цепочки.</p>
     *
     * @throws IllegalArgumentException если указанный элемент равен null
     */
    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Элемент не может быть null");
        }

        ChainElement<T> newElement = new ChainElement<>(item);

        if (start == null) {
            start = newElement;
            end = newElement;
        } else {
            end.link = newElement;
            end = newElement;
        }
        elementCount++;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Реализация осуществляет последовательный проход от начала цепочки
     * до указанной позиции.</p>
     *
     * @throws IndexOutOfBoundsException если позиция выходит за допустимые пределы
     */
    @Override
    public T get(int position) {
        validatePosition(position);

        ChainElement<T> targetElement = locateElement(position);
        return targetElement.value;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Реализация обрабатывает три сценария:
     * <ul>
     *   <li>Извлечение из начала цепочки.</li>
     *   <li>Извлечение из завершения цепочки.</li>
     *   <li>Извлечение из середины цепочки.</li>
     * </ul>
     * </p>
     *
     * @throws IndexOutOfBoundsException если позиция выходит за допустимые пределы
     */
    @Override
    public T removeAt(int position) {
        validatePosition(position);

        if (position == 0) {
            return extractFirstElement();
        }

        ChainElement<T> precedingElement = locateElement(position - 1);
        T extractedValue = precedingElement.link.value;
        precedingElement.link = precedingElement.link.link;

        if (precedingElement.link == null) {
            end = precedingElement;
        }

        elementCount--;
        return extractedValue;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Реализация находит первое совпадение элемента и исключает его из цепочки.</p>
     *
     * @return true если элемент был обнаружен и извлечен, false если элемент не найден
     */
    @Override
    public boolean removeItem(T item) {
        if (item == null || start == null) {
            return false;
        }

        if (start.value.equals(item)) {
            extractFirstElement();
            return true;
        }

        ChainElement<T> currentElement = start;
        while (currentElement.link != null && !currentElement.link.value.equals(item)) {
            currentElement = currentElement.link;
        }

        if (currentElement.link != null) {
            currentElement.link = currentElement.link.link;

            if (currentElement.link == null) {
                end = currentElement;
            }

            elementCount--;
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return текущее количество элементов в цепочке
     */
    @Override
    public int getLength() {
        return elementCount;
    }

    /**
     * {@inheritDoc}
     *
     * @return true если цепочка не содержит элементов, иначе false
     */
    @Override
    public boolean isChainEmpty() {
        return elementCount == 0;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Реализация обнуляет все связи, позволяя сборщику мусора
     * освободить память, занятую звеньями цепочки.</p>
     */
    @Override
    public void wipeAll() {
        start = null;
        end = null;
        elementCount = 0;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Реализация использует метод findPosition для проверки присутствия элемента.</p>
     *
     * @return true если элемент присутствует в цепочке, иначе false
     */
    @Override
    public boolean hasElement(T item) {
        return findPosition(item) != -1;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Реализация выполняет последовательный поиск от начала цепочки.</p>
     *
     * @return позицию первого обнаруженного элемента или -1 если элемент отсутствует
     */
    @Override
    public int findPosition(T item) {
        if (item == null) {
            return -1;
        }

        ChainElement<T> currentElement = start;
        int currentPosition = 0;

        while (currentElement != null) {
            if (currentElement.value.equals(item)) {
                return currentPosition;
            }
            currentElement = currentElement.link;
            currentPosition++;
        }

        return -1;
    }

    /**
     * Формирует текстовое представление содержимого цепочки.
     * Элементы отображаются в порядке их следования, обрамленные квадратными скобками.
     *
     * @return строковое представление цепочки элементов
     */
    @Override
    public String toString() {
        if (start == null) {
            return "[]";
        }

        StringBuilder resultBuilder = new StringBuilder("[");
        ChainElement<T> currentElement = start;

        while (currentElement != null) {
            resultBuilder.append(currentElement.value);
            if (currentElement.link != null) {
                resultBuilder.append(", ");
            }
            currentElement = currentElement.link;
        }
        resultBuilder.append("]");
        return resultBuilder.toString();
    }

    // Внутренние вспомогательные методы

    /**
     * Извлекает и возвращает начальный элемент цепочки.
     *
     * <p>Этот метод применяется при извлечении элемента с позиции 0
     * или при извлечении элемента по значению из начала цепочки.</p>
     *
     * @return значение извлеченного начального элемента
     */
    private T extractFirstElement() {
        T extractedValue = start.value;
        start = start.link;

        if (start == null) {
            end = null;
        }

        elementCount--;
        return extractedValue;
    }

    /**
     * Находит звено по указанной позиции в цепочке.
     *
     * <p>Метод выполняет последовательный обход от начала цепочки
     * до указанной позиции. Используется внутренними методами
     * для доступа к звеньям цепочки.</p>
     *
     * @param position позиция искомого звена
     * @return звено на указанной позиции
     */
    private ChainElement<T> locateElement(int position) {
        ChainElement<T> currentElement = start;
        for (int i = 0; i < position; i++) {
            currentElement = currentElement.link;
        }
        return currentElement;
    }

    /**
     * Проверяет корректность указанной позиции.
     *
     * <p>Метод проверяет, что позиция находится в диапазоне
     * от 0 (включительно) до elementCount (исключительно).</p>
     *
     * @param position проверяемая позиция
     * @throws IndexOutOfBoundsException если позиция некорректна
     */
    private void validatePosition(int position) {
        if (position < 0 || position >= elementCount) {
            throw new IndexOutOfBoundsException(
                    String.format("Некорректная позиция: %d, Размер: %d", position, elementCount));
        }
    }

    /**
     * Проверяет, является ли цепочка циклической.
     *
     * @return true если в цепочке обнаружен цикл, иначе false
     */
    public boolean hasCycle() {
        if (start == null) {
            return false;
        }

        ChainElement<T> slow = start;
        ChainElement<T> fast = start;

        while (fast != null && fast.link != null) {
            slow = slow.link;
            fast = fast.link.link;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}