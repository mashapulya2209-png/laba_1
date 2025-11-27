package test.java;

import main.java.org.example.LinkedListContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности цепочечной структуры данных.
 *
 * <p>Данный класс проводит комплексную проверку всех операций структуры,
 * включая граничные условия и особые случаи работы с элементами.</p>
 * @see LinkedListContainer
 */
class LinkedListContainerTest {
    /**
     * Тестируемая структура данных. Инициализируется заново перед каждым тестом.
     */
    private LinkedListContainer<String> dataChain;

    /**
     * Метод подготовки, выполняемый перед каждым тестовым случаем.
     * Создает новую пустую структуру для обеспечения изолированности тестов.
     */
    @BeforeEach
    void initialize() {
        dataChain = new LinkedListContainer<>();
    }

    /**
     * Проверяет базовые операции добавления и извлечения элементов.
     *
     * <p>Контролирует:
     * <ul>
     *   <li>Корректность добавления элементов в структуру</li>
     *   <li>Правильность подсчета количества элементов</li>
     *   <li>Сохранение порядка элементов согласно последовательности добавления</li>
     * </ul>
     * </p>
     */
    @Test
    void testElementInsertionAndRetrieval() {
        // Добавляем три тестовых элемента
        dataChain.add("Яблоко");
        dataChain.add("Банан");
        dataChain.add("Апельсин");

        // Проверяем общее количество элементов
        assertEquals(3, dataChain.getLength());
        // Проверяем корректность извлечения элементов по позициям
        assertEquals("Яблоко", dataChain.get(0));
        assertEquals("Банан", dataChain.get(1));
        assertEquals("Апельсин", dataChain.get(2));
    }

    /**
     * Проверяет реакцию на попытку добавления нулевого элемента.
     *
     * <p>Ожидается, что метод add сгенерирует IllegalArgumentException
     * при попытке добавления null значения.</p>
     */
    @Test
    void testNullElementInsertionThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> dataChain.add(null));
    }

    /**
     * Проверяет обработку некорректных позиций при извлечении элементов.
     *
     * <p>Анализирует пограничные ситуации:
     * <ul>
     *   <li>Отрицательная позиция</li>
     *   <li>Позиция, превышающая текущий размер структуры</li>
     * </ul>
     * Ожидается возникновение IndexOutOfBoundsException в обоих случаях.
     * </p>
     */
    @Test
    void testElementRetrievalWithInvalidPosition() {
        dataChain.add("Тестовый элемент");

        assertThrows(IndexOutOfBoundsException.class, () -> dataChain.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> dataChain.get(5));
    }

    /**
     * Проверяет извлечение элемента по позиции из середины цепочки.
     *
     * <p>Контролирует:
     * <ul>
     *   <li>Корректность возвращаемого значения (извлеченный элемент)</li>
     *   <li>Обновление счетчика элементов</li>
     *   <li>Правильность перестройки связей между оставшимися элементами</li>
     * </ul>
     * </p>
     */
    @Test
    void testElementExtractionByPosition() {
        dataChain.add("Элемент A");
        dataChain.add("Элемент B");
        dataChain.add("Элемент C");

        String extracted = dataChain.removeAt(1);
        assertEquals("Элемент B", extracted);
        assertEquals(2, dataChain.getLength());
        assertEquals("Элемент A", dataChain.get(0));
        assertEquals("Элемент C", dataChain.get(1));
    }

    /**
     * Проверяет извлечение начального элемента цепочки.
     *
     * <p>Анализирует особый случай, когда извлекаемый элемент является
     * первым в структуре. Должна корректно обновиться ссылка на начало цепочки.</p>
     */
    @Test
    void testFirstElementExtraction() {
        dataChain.add("Первый");
        dataChain.add("Второй");

        String extracted = dataChain.removeAt(0);
        assertEquals("Первый", extracted);
        assertEquals(1, dataChain.getLength());
        assertEquals("Второй", dataChain.get(0));
    }

    /**
     * Проверяет извлечение конечного элемента цепочки.
     *
     * <p>Анализирует особый случай, когда извлекаемый элемент является
     * последним в структуре. Должна корректно обновиться ссылка на конец цепочки.</p>
     */
    @Test
    void testLastElementExtraction() {
        dataChain.add("Начальный");
        dataChain.add("Конечный");

        String extracted = dataChain.removeAt(1);
        assertEquals("Конечный", extracted);
        assertEquals(1, dataChain.getLength());
        assertEquals("Начальный", dataChain.get(0));
    }

    /**
     * Проверяет удаление элемента по его значению.
     *
     * <p>Контролирует:
     * <ul>
     *   <li>Корректность возвращаемого значения (true при успешном удалении)</li>
     *   <li>Уменьшение общего количества элементов</li>
     *   <li>Фактическое отсутствие удаленного элемента в структуре</li>
     * </ul>
     * </p>
     */
    @Test
    void testElementRemovalByValue() {
        dataChain.add("Красный");
        dataChain.add("Зеленый");
        dataChain.add("Синий");

        assertTrue(dataChain.removeItem("Зеленый"));
        assertEquals(2, dataChain.getLength());
        assertFalse(dataChain.hasElement("Зеленый"));
    }

    /**
     * Проверяет попытку удаления отсутствующего элемента.
     *
     * <p>Ожидается, что метод вернет false и количество элементов останется неизменным.</p>
     */
    @Test
    void testNonExistentElementRemoval() {
        dataChain.add("Существующий");

        assertFalse(dataChain.removeItem("Отсутствующий"));
        assertEquals(1, dataChain.getLength());
    }

    /**
     * Проверяет метод определения пустоты структуры.
     *
     * <p>Анализирует все возможные состояния:
     * <ul>
     *   <li>Изначально пустая структура</li>
     *   <li>Структура после добавления элементов</li>
     *   <li>Структура после полной очистки</li>
     * </ul>
     * </p>
     */
    @Test
    void testStructureEmptiness() {
        assertTrue(dataChain.isChainEmpty());

        dataChain.add("Тестовый элемент");
        assertFalse(dataChain.isChainEmpty());

        dataChain.removeAt(0);
        assertTrue(dataChain.isChainEmpty());
    }

    /**
     * Проверяет метод полной очистки структуры.
     *
     * <p>Контролирует, что после выполнения wipeAll() структура становится пустой
     * и счетчик элементов обнуляется.</p>
     */
    @Test
    void testCompleteStructureClearing() {
        dataChain.add("Позиция 1");
        dataChain.add("Позиция 2");
        dataChain.add("Позиция 3");

        dataChain.wipeAll();

        assertTrue(dataChain.isChainEmpty());
        assertEquals(0, dataChain.getLength());
    }

    /**
     * Проверяет метод определения наличия элемента в структуре.
     *
     * <p>Анализирует как присутствие существующих элементов, так и отсутствие несуществующих.</p>
     */
    @Test
    void testElementPresenceVerification() {
        dataChain.add("Москва");
        dataChain.add("Санкт-Петербург");

        assertTrue(dataChain.hasElement("Москва"));
        assertTrue(dataChain.hasElement("Санкт-Петербург"));
        assertFalse(dataChain.hasElement("Казань"));
    }

    /**
     * Проверяет поиск позиции элемента в структуре.
     *
     * <p>Контролирует:
     * <ul>
     *   <li>Поиск первого вхождения элемента</li>
     *   <li>Обработку повторяющихся элементов</li>
     *   <li>Возврат -1 для отсутствующих элементов</li>
     * </ul>
     * </p>
     */
    @Test
    void testElementPositionFinding() {
        dataChain.add("Повтор");
        dataChain.add("Уникальный");
        dataChain.add("Повтор"); // дубликат

        assertEquals(0, dataChain.findPosition("Повтор"));
        assertEquals(1, dataChain.findPosition("Уникальный"));
        assertEquals(-1, dataChain.findPosition("Отсутствующий"));
    }

    /**
     * Проверяет текстовое представление структуры.
     *
     * <p>Анализирует формат вывода для различных состояний:
     * <ul>
     *   <li>Пустая структура</li>
     *   <li>Структура с одним элементом</li>
     *   <li>Структура с несколькими элементами</li>
     * </ul>
     * </p>
     */
    @Test
    void testTextRepresentation() {
        assertEquals("[]", dataChain.toString());

        dataChain.add("Один");
        assertEquals("[Один]", dataChain.toString());

        dataChain.add("Два");
        dataChain.add("Три");
        assertEquals("[Один, Два, Три]", dataChain.toString());
    }

    /**
     * Проверяет работу структуры с большим объемом данных.
     *
     * <p>Анализирует производительность и корректность функционирования
     * при добавлении и извлечении 500 элементов.</p>
     */
    @Test
    void testHighVolumeOperations() {
        int elementCount = 500;

        for (int i = 0; i < elementCount; i++) {
            dataChain.add("Объект" + i);
        }

        assertEquals(elementCount, dataChain.getLength());

        for (int i = 0; i < elementCount; i++) {
            assertEquals("Объект" + i, dataChain.get(i));
        }
    }

    /**
     * Проверяет обнаружение циклических ссылок в структуре.
     *
     * <p>Тестирует метод hasCycle на различных конфигурациях цепочки.</p>
     */
    @Test
    void testCycleDetection() {
        // Тест на отсутствие циклов в нормальной цепочке
        dataChain.add("A");
        dataChain.add("B");
        dataChain.add("C");
        assertFalse(dataChain.hasCycle());
    }
}