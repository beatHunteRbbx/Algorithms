package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Если на входе граф с циклами, бросить IllegalArgumentException
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {

        Map<Graph.Vertex, Boolean> visitedVerticesMap = new HashMap<>();
        for (Graph.Vertex vertex : graph.getVertices()) visitedVerticesMap.put(vertex, false);



        Set<Graph.Vertex> independentVerticesSet = new HashSet<>();
        Deque<Graph.Vertex> arrVertices = new LinkedList<>(graph.getVertices()); //K
        Deque<Graph.Vertex> independentVertexList = new LinkedList<>();          //M
        Deque<Graph.Vertex> visitedVerticesList = new LinkedList<>();            //P
        Deque<Graph.Vertex> verticesList = new LinkedList<>();

        //поиск циклов в графе
//        if (!graph.getVertices().isEmpty()) loopSearch(graph, arrVertices.getFirst(), visitedVerticesMap);

        //Алгоритм Брона-Кербоша
        while(!arrVertices.isEmpty() || !independentVertexList.isEmpty()) {
            Graph.Vertex vertex = null;
            if (!arrVertices.isEmpty()) { //пока множество кандидатов не пустое
                vertex = arrVertices.getFirst(); //берем вершину из множества кадидатов
                independentVertexList.offer(vertex); //помещаем её в возможное независимое множество
                for (Graph.Vertex neighbour : graph.getNeighbors(vertex)) arrVertices.remove(neighbour); //удаляем из множества кандидатов всех соседей вершины vertex
                arrVertices.remove(vertex); //удаляем саму вершину vertex из множества кандидатов
//                for (Graph.Vertex neighbour : graph.getNeighbors(vertex)) visitedVerticesList.remove(neighbour);
            }
            else {  //если множество кандидатов пустое
//                if (visitedVerticesList.isEmpty()) {
                independentVerticesSet.addAll(independentVertexList); //преобразуем List независимых вершин в Set
                independentVertexList.clear();  //очищаем List для дальнейшего нового составления
//                }
//                arrVertices.remove(vertex);
//                visitedVerticesList.offer(vertex);
            }
        }
        return independentVerticesSet;
    }

    static void loopSearch(Graph graph, Graph.Vertex vertex, Map<Graph.Vertex, Boolean> visitedVerticesMap) {
        boolean hasLoop = false;
        List<Graph.Vertex> arrVertices = new ArrayList<>(graph.getVertices());

        visitedVerticesMap.put(vertex, true);

        Set<Graph.Vertex> visitedNeighbours = new HashSet<>();
        for (Graph.Vertex neighbour : graph.getNeighbors(vertex)) {
            if (visitedVerticesMap.get(neighbour) == true) visitedNeighbours.add(neighbour);
            else loopSearch(graph, neighbour, visitedVerticesMap);
        }
        if (visitedNeighbours.equals(graph.getNeighbors(vertex)) && visitedNeighbours.size() > 1) hasLoop = true;
        if (hasLoop) throw new IllegalArgumentException();
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        Path longestPath = new Path();
        int maxLength = -1;
        Deque<Path> pathDeque = new LinkedList<>();
        //составляем все возможные начала путей в графе
        for (Graph.Vertex vertex : graph.getVertices()) pathDeque.offer(new Path(vertex));
        while (!pathDeque.isEmpty()) {

            //поочередно вытаскиваем пути, для дальнейшего их дополнения
            Path path = pathDeque.poll();

            for (Graph.Vertex neighbor : graph.getNeighbors(path.getVertices().get(path.getLength()))) {

                //дополняем пути и помещаем их в pathDeque
                if (!path.contains(neighbor)) pathDeque.offer(new Path(path, graph, neighbor));
//                if (path.getVertices().contains(edge.getBegin())) break;
//                if (edge.getBegin() != vertex) {
//                    pathDeque.offer(edge.getBegin());
//                }
//                if (edge.getEnd() != vertex) {
//                    pathDeque.offer(edge.getEnd());
//                }
            }

            if (path.getLength() > maxLength) {
                maxLength = path.getLength();
                longestPath = path;
            }
        }
        return longestPath;
    }
}
