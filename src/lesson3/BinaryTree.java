package lesson3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    BinaryTree() {
    }
    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T value) {
        Node<T> closest = find(value);
        int comparison = closest == null ? -1 : value.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(value);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     *
     *
     * Сложность
     * Время: O(log(n))
     * Память: O(1)
     */
    @Override
    public boolean  remove(Object o) {
        if (find((T) o) ==  null) return false; //если элемент в дереве не найден, возвращаем false
        root = removeNode(root, new Node<>((T) o));
        size--;
        return true;
    }

    private Node<T> removeNode(Node<T> root, Node<T> removableNode) {
      if (root == null) return null;
      if (removableNode.value.equals(root.value)) {
          if (root.left != null && root.right != null) { //если у элемента два дерева-потомка, то перед его удалением
              Node<T> node = new Node<>(minNode(root.right)); //стараемся "сбалансировать" дерево - ищем в левой части правого поддерева минимальный элемент
              node.left = root.left;  //переопределяем ссылки на элементы
              node.right = root.right; //у минимального элемента и корня дерева
              root = node;
              root.right = removeNode(root.right, root); //рекурсивно удаляем минимальный элемент из дерева
          }
          else {
              if (root.left == null && root.right == null) { //если у элемента нет потомков
                  return null;                        //удаляем его из дерева (делаем значение ссылки для родителя null)
              }
              else if (root.left != null) return root.left;
              else return root.right;
          }
      }
      else {
          if ((removableNode.value).compareTo(root.value) < 0) {
              root.left = removeNode(root.left, removableNode);
          } else {
              root.right = removeNode(root.right, removableNode);
          }
      }
      return root;
    }

    private T minNode(Node<T> node) {
        if (node == null) return null;
        Node<T> currentNode = node;
        while (currentNode.left != null) {
            currentNode = currentNode.left;
        }
        return currentNode.value;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> currentNode = null;
        private Node<T> previousNode = null;
        private Stack<Node<T>> parentsStack = new Stack<>();

        private BinaryTreeIterator() {
            if (root == null) return;
            currentNode = root;
            parentsStack.push(null);
            while (currentNode.left != null) { //находим самый мнимальный элемент в дереве
                parentsStack.push(currentNode);
                currentNode = currentNode.left;
            }
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         *
         * Сложность
         * Время: O(1)
         * Память: O(1)
         */
        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        /**
         * Поиск следующего элемента
         * Средняя
         *
         * Сложность
         * Время: O(log(n))   n - число элементов в дереве
         * Память: O(1)
         */
        @Override
        public T next() {
            previousNode = currentNode;
            currentNode = findNext(currentNode);
            return previousNode.value;
        }

        private Node<T> findNext(Node<T> node) {
            if (node.right != null) {
                parentsStack.push(node);
                return findMinNodeWithParents(node.right);
            }
            Node<T> parent = parentsStack.pop();
            while (parent != null && node == parent.right) {
                node = parent;
                parent = parentsStack.pop();
            }
            return parent;
        }

        private Node<T> findNodeWithParents(Node<T> startNode, T value) {
            if (value.compareTo(startNode.value) > 0){
                parentsStack.push(startNode);
                if (startNode.right == null) return startNode;
                return findNodeWithParents(startNode.right, value);
            }
            else if (value.compareTo(startNode.value) < 0) {
                parentsStack.push(startNode);
                if (startNode.left == null) return startNode;
                return findNodeWithParents(startNode.left, value);
            }
            else return startNode;
        }

        /*
        В методе ищется мнимальный элемент в левом поддереве
        И пока идет поиск все элементы до минимального (родители) помещаются в parentsStack
         */
        private Node<T> findMinNodeWithParents(Node<T> node) {
            if (node == null) return null;
            Node<T> currentNode = node;
            while (currentNode.left != null) {
                parentsStack.push(currentNode);
                currentNode = currentNode.left;
            }
            return currentNode;
        }
        /**
         * Удаление следующего элемента
         * Сложная
         *
         * Сложность
         * Время: O(n*log(n))   n - число элементов дерева
         * Память: O(1)
         */
        @Override
        public void remove() {
            BinaryTree.this.remove(previousNode.value);
            while (!parentsStack.empty()) parentsStack.pop();
            parentsStack.push(null); //чтобы не вылетал EmptyStackException
            if (currentNode == null) return;
            currentNode = findNodeWithParents(root, currentNode.value);
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     *
     * Сложность
     * Время: O(1)
     * Память: O(n)   n - количество элементов в дереве
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new SubBinaryTree(this, fromElement, toElement);
    }

    private class SubBinaryTree extends BinaryTree<T>{
        BinaryTree<T> binaryTree;
        T fromElement;
        T toElement;

        SubBinaryTree(BinaryTree<T> binaryTree, T fromElement, T toElement) {
            this.binaryTree = binaryTree;
            this.fromElement = fromElement;
            this.toElement = toElement;
        }


        @Override
        public boolean add(T value) {
            if (!inRange(value)) throw new IllegalArgumentException();
            return binaryTree.add(value);
        }

        @Override
        public boolean remove (Object object) {
            return binaryTree.remove(object);
        }

        @Override
        public boolean contains(Object object) {
            if (!inRange((T) object)) return false;
            return binaryTree.contains(object);
        }

        @Override
        public int size() {
            int size = 0;
            for (T value : binaryTree) {
                if (inRange(value)) size++;
            }
            return size;
        }

        private boolean inRange(T value) {
            /*
             * Присваиваем
             *
             * compareWithBottom = 0
             * compareWithTop = -1
             *
             * чтобы в методах tailSet() и headSet() не учитывать границы поддерева
             */
            int compareWithBottom = 0;
            int compareWithTop = -1;
            if (fromElement != null) compareWithBottom = value.compareTo(fromElement);
            if (toElement != null) compareWithTop = value.compareTo(toElement);
            return  compareWithBottom >= 0 && compareWithTop < 0;
        }

        @NotNull
        @Override
        public Iterator<T> iterator() { return new SubBinaryTreeIterator(); }

        public class SubBinaryTreeIterator extends BinaryTreeIterator {

            private Node<T> currentNode = null;
            private Node<T> previousNode = null;
            private Stack<Node<T>> parentsStack = new Stack<>();

            private SubBinaryTreeIterator() {
                if (root == null) return;
                currentNode = root;
                parentsStack.push(null);
                while (currentNode.left != null) { //находим самый мнимальный элемент в дереве
                    parentsStack.push(currentNode);
                    currentNode = currentNode.left;
                }
            }

            @Override
            public boolean hasNext() {
                return currentNode != null && inRange(currentNode.value);
            }

            @Override
            public T next() {
                previousNode = currentNode;
                currentNode = findNext(currentNode);
                return previousNode.value;
            }

            private Node<T> findNext(Node<T> node) {
                if (node.right != null) {
                    parentsStack.push(node);
                    return findMinNodeWithParents(node.right);
                }
                Node<T> parent = parentsStack.pop();
                while (parent != null && node == parent.right) {
                    node = parent;
                    parent = parentsStack.pop();
                }
                return parent;
            }

            private Node<T> findNodeWithParents(Node<T> startNode, T value) {
                if (value.compareTo(startNode.value) > 0){
                    parentsStack.push(startNode);
                    if (startNode.right == null) return startNode;
                    return findNodeWithParents(startNode.right, value);
                }
                else if (value.compareTo(startNode.value) < 0) {
                    parentsStack.push(startNode);
                    if (startNode.left == null) return startNode;
                    return findNodeWithParents(startNode.left, value);
                }
                else return startNode;
            }

            /*
            В методе ищется мнимальный элемент в левом поддереве
            И пока идет поиск все элементы до минимального (родители) помещаются в parentsStack
             */
            private Node<T> findMinNodeWithParents(Node<T> node) {
                if (node == null) return null;
                Node<T> currentNode = node;
                while (currentNode.left != null) {
                    parentsStack.push(currentNode);
                    currentNode = currentNode.left;
                }
                return currentNode;
            }

            @Override
            public void remove() {
                BinaryTree.this.remove(previousNode.value);
                while (!parentsStack.empty()) parentsStack.pop();
                parentsStack.push(null); //чтобы не вылетал EmptyStackException
                if (currentNode == null) return;
                currentNode = findNodeWithParents(root, currentNode.value);
            }
        }
    }
    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     *
     * Сложность
     * Время: O(n)     n - число элементов главного дерева
     * Память: O(n)    n - число элементов поддерева
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        //нужно составить множество из элементов, которые находятся в левом поддереве заданного элемента и из всех
        //элементов, идущих выше заданного элемента
        return new SubBinaryTree(this, null, toElement);
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     *
     * Сложность
     * Время: O(n)     n - число элементов главного дерева
     * Память: O(n)    n - число элементов поддерева
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        //нужно составить множество из элементов, которые находятся в правом поддереве заданного элемента и
        //из данного элемента
        return new SubBinaryTree(this, fromElement, null);
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
