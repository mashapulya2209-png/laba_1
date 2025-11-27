package main.java.org.example;

/**
 * Пример использования структуры данных "Цепочка элементов".
 *
 * <p>Данный класс наглядно показывает работу с последовательностью элементов:
 * добавление, извлечение, поиск, удаление и другие базовые операции.</p>
 *
 * @see LinkedListContainer
 */
public class Main {
    /**
     * Точка входа в программу для демонстрации функциональности цепочки элементов.
     *
     * <p>Программа поэтапно показывает:
     * <ol>
     *   <li>Инициализацию структуры</li>
     *   <li>Заполнение элементами</li>
     *   <li>Получение элементов по позициям</li>
     *   <li>Поиск элементов в структуре</li>
     *   <li>Исключение элементов</li>
     *   <li>Очистку всей структуры</li>
     * </ol>
     * </p>
     */
    public static void main(String[] args) {
        // Инициализируем цепочку для текстовых элементов
        LinkedListContainer<String> elementChain = new LinkedListContainer<>();

        System.out.println("=== Демонстрация работы цепочки элементов ===");

        // Заполняем цепочку различными элементами
        elementChain.add("Яблоко");
        elementChain.add("Банан");
        elementChain.add("Апельсин");
        elementChain.add("Виноград");

        // Отображаем начальное состояние
        System.out.println("Исходная цепочка: " + elementChain);
        System.out.println("Количество элементов: " + elementChain.getLength());
        System.out.println("Цепочка пуста: " + elementChain.isChainEmpty());

        // Получаем элементы по конкретным позициям
        System.out.println("\n--- Получение элементов по позициям ---");
        System.out.println("Элемент на позиции 1: " + elementChain.get(1));
        System.out.println("Элемент на позиции 2: " + elementChain.get(2));

        // Проверяем наличие определенных элементов
        System.out.println("\n--- Проверка наличия элементов ---");
        System.out.println("Содержит 'Яблоко': " + elementChain.hasElement("Яблоко"));
        System.out.println("Содержит 'Манго': " + elementChain.hasElement("Манго"));

        // Определяем позиции элементов в цепочке
        System.out.println("\n--- Определение позиций элементов ---");
        System.out.println("Позиция 'Апельсин': " + elementChain.findPosition("Апельсин"));
        System.out.println("Позиция 'Манго': " + elementChain.findPosition("Манго"));

        // Удаляем элемент по указанной позиции
        System.out.println("\n--- Удаление элемента по позиции ---");
        String extractedElement = elementChain.removeAt(1);
        System.out.println("Извлеченный элемент с позиции 1: " + extractedElement);
        System.out.println("Цепочка после извлечения: " + elementChain);

        // Удаляем элемент по значению
        System.out.println("\n--- Удаление элемента по значению ---");
        boolean removalResult = elementChain.removeItem("Виноград");
        System.out.println("'Виноград' удален: " + removalResult);
        System.out.println("Цепочка после удаления: " + elementChain);

        // Полностью очищаем цепочку
        System.out.println("\n--- Очистка цепочки ---");
        elementChain.wipeAll();
        System.out.println("Цепочка после очистки: " + elementChain);
        System.out.println("Количество элементов: " + elementChain.getLength());
        System.out.println("Цепочка пуста: " + elementChain.isChainEmpty());

        // Дополнительная демонстрация с числовыми элементами
        System.out.println("\n--- Работа с числовой цепочкой ---");
        LinkedListContainer<Integer> numberChain = new LinkedListContainer<>();

        numberChain.add(100);
        numberChain.add(200);
        numberChain.add(300);

        System.out.println("Числовая цепочка: " + numberChain);
        System.out.println("Сумма первых двух элементов: " +
                (numberChain.get(0) + numberChain.get(1)));

        System.out.println("\n=== Демонстрация завершена ===");
    }
}