package lesson3

import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

abstract class AbstractHeadTailTest {
    private lateinit var tree: SortedSet<Int>
    private lateinit var randomTree: SortedSet<Int>
    private val randomTreeSize = 1000
    private val randomValues = mutableListOf<Int>()

    private lateinit var myTree: SortedSet<Int>

    protected fun fillTree(create: () -> SortedSet<Int>) {
        this.tree = create()
        //В произвольном порядке добавим числа от 1 до 10
        tree.add(5)
        tree.add(1)
        tree.add(2)
        tree.add(7)
        tree.add(9)
        tree.add(10)
        tree.add(8)
        tree.add(4)
        tree.add(3)
        tree.add(6)

        this.randomTree = create()
        val random = Random()
        for (i in 0 until randomTreeSize) {
            val randomValue = random.nextInt(randomTreeSize) + 1
            if (randomTree.add(randomValue)) {
                randomValues.add(randomValue)
            }
        }
    }

    protected fun fillMyTree(create: () -> SortedSet<Int>) {
        this.myTree = create()
        for (i in 1..10) myTree.add(i)
        for (i in 20 downTo 11) myTree.add(i)
    }

    protected fun doHeadSetTest() {
        var set: SortedSet<Int> = tree.headSet(5)
        assertEquals(true, set.contains(1))
        assertEquals(true, set.contains(2))
        assertEquals(true, set.contains(3))
        assertEquals(true, set.contains(4))
        assertEquals(false, set.contains(5))
        assertEquals(false, set.contains(6))
        assertEquals(false, set.contains(7))
        assertEquals(false, set.contains(8))
        assertEquals(false, set.contains(9))
        assertEquals(false, set.contains(10))


        set = tree.headSet(127)
        for (i in 1..10)
            assertEquals(true, set.contains(i))

        val random = Random()
        val toElement = random.nextInt(randomTree.size - 1) + 1
        val randomHeadSet = randomTree.headSet(toElement)
        randomValues.forEach { element ->
            assertEquals(element in 0 until toElement, randomHeadSet.contains(element))
        }
    }

    protected fun doTailSetTest() {
        var set: SortedSet<Int> = tree.tailSet(5)
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(2))
        assertEquals(false, set.contains(3))
        assertEquals(false, set.contains(4))
        assertEquals(true, set.contains(5))
        assertEquals(true, set.contains(6))
        assertEquals(true, set.contains(7))
        assertEquals(true, set.contains(8))
        assertEquals(true, set.contains(9))
        assertEquals(true, set.contains(10))

        set = tree.tailSet(-128)
        for (i in 1..10)
            assertEquals(true, set.contains(i))

        set = tree.tailSet(5)
        for (i in 1..10) {
            if (i < 5) assertEquals(false, set.contains(i))
            else assertEquals(true, set.contains(i))
        }
    }

    protected fun doHeadSetRelationTest() {
        val set: SortedSet<Int> = tree.headSet(7)
        assertEquals(6, set.size)
        assertEquals(10, tree.size)
        tree.add(0)
        assertTrue(set.contains(0))
        set.add(-2)
        assertTrue(tree.contains(-2))
        tree.add(12)
        assertFalse(set.contains(12))
        assertFailsWith<IllegalArgumentException> { set.add(8) }
        assertEquals(8, set.size)
        assertEquals(13, tree.size)

        val mySet: SortedSet<Int> = myTree.headSet(14)
        assertEquals(13, mySet.size)
        assertEquals(20, myTree.size)
        myTree.add(21)
        myTree.add(30)
        assertFailsWith<IllegalArgumentException> { mySet.add(30) }
        mySet.add(4)
        mySet.add(-10)
        myTree.add(15)
        assertEquals(14, mySet.size)
        assertEquals(23, myTree.size)
    }

    protected fun doTailSetRelationTest() {
        val set: SortedSet<Int> = tree.tailSet(4)
        assertEquals(7, set.size)
        assertEquals(10, tree.size)
        tree.add(12)
        assertTrue(set.contains(12))
        set.add(42)
        assertTrue(tree.contains(42))
        tree.add(0)
        assertFalse(set.contains(0))
        assertFailsWith<IllegalArgumentException> { set.add(-2) }
        assertEquals(9, set.size)
        assertEquals(13, tree.size)
    }

    protected fun doSubSetTest() {
        val smallSet: SortedSet<Int> = tree.subSet(3, 8)
        assertEquals(false, smallSet.contains(1))
        assertEquals(false, smallSet.contains(2))
        assertEquals(true, smallSet.contains(3))
        assertEquals(true, smallSet.contains(4))
        assertEquals(true, smallSet.contains(5))
        assertEquals(true, smallSet.contains(6))
        assertEquals(true, smallSet.contains(7))
        assertEquals(false, smallSet.contains(8))
        assertEquals(false, smallSet.contains(9))
        assertEquals(false, smallSet.contains(10))

        assertFailsWith<IllegalArgumentException> { smallSet.add(2) }
        assertFailsWith<IllegalArgumentException> { smallSet.add(9) }

        val allSet = tree.subSet(-128, 128)
        for (i in 1..10)
            assertEquals(true, allSet.contains(i))

        val random = Random()
        val toElement = random.nextInt(randomTreeSize) + 1
        val fromElement = random.nextInt(toElement - 1) + 1

        val randomSubset = randomTree.subSet(fromElement, toElement)
        randomValues.forEach { element ->
            assertEquals(element in fromElement until toElement, randomSubset.contains(element))
        }

        val mySubSet: SortedSet<Int> = tree.subSet(6, 6)
        assertEquals(0, mySubSet.size)

        val mySubSet1: SortedSet<Int> = tree.subSet(tree.first(), tree.last())
        assertEquals(mySubSet1.size, mySubSet1.size)
    }

    protected fun doSubSetRelationTest() {
        val set: SortedSet<Int> = tree.subSet(2, 15)
        assertEquals(9, set.size)
        assertEquals(10, tree.size)
        tree.add(11)
        assertTrue(set.contains(11))
        set.add(14)
        assertTrue(tree.contains(14))
        tree.add(0)
        assertFalse(set.contains(0))
        tree.add(15)
        assertFalse(set.contains(15))
        assertFailsWith<IllegalArgumentException> { set.add(1) }
        assertFailsWith<IllegalArgumentException> { set.add(20) }
        assertEquals(11, set.size)
        assertEquals(14, tree.size)

        val mySubSet: SortedSet<Int> = myTree.subSet(13, 37)
        assertEquals(8, mySubSet.size)
        assertEquals(20, myTree.size)
        mySubSet.add(28)
        assertFailsWith<IllegalArgumentException> { mySubSet.add(69) }
        mySubSet.add(34)
        myTree.add(21)
        assertFailsWith<IllegalArgumentException> { mySubSet.add(2) }
        myTree.add(23)
        myTree.add(4)
        myTree.add(-1)
        assertEquals(12, mySubSet.size)
        assertEquals(25, myTree.size)
    }

    protected fun testSubBinaryTreeIterator(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..1000) {
            val list = mutableListOf<Int>()
            for (i in 1..10) {
                list.add(random.nextInt(1000))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            assertFalse(
                binarySet.subSet(0, 100).iterator().hasNext(),
                "Iterator of empty set should not have next element"
            )
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val randomSize = random.nextInt(1000)
            val treeIt = treeSet.subSet(0, randomSize).iterator()
            val binaryIt = binarySet.subSet(0, randomSize).iterator()
            println("Traversing $list")
            while (treeIt.hasNext()) {
                assertEquals(treeIt.next(), binaryIt.next(), "Incorrect iterator state while iterating $treeSet")
            }
            val iterator1 = binarySet.subSet(0, randomSize).iterator()
            val iterator2 = binarySet.subSet(0, randomSize).iterator()
            println("Consistency check for hasNext $list")
            // hasNext call should not affect iterator position
            while (iterator1.hasNext()) {
                assertEquals(
                    iterator2.next(), iterator1.next(),
                    "Call of iterator.hasNext() changes its state while iterating $treeSet"
                )
            }
        }
    }

    protected fun testSubBinaryTreeIteratorRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..10) {
                list.add(random.nextInt(1000))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)] + 1  // +1 так как верхняя граница не учитывается
            treeSet.subSet(0, toRemove).remove(toRemove)
            println("Removing $toRemove from $list")
            val iterator = binarySet.subSet(0, toRemove).iterator()
            var counter = binarySet.subSet(0, toRemove).size
            while (iterator.hasNext()) {
                val element = iterator.next()
                counter--
                print("$element ")
                if (element == toRemove) {
                    iterator.remove()
                }
            }
        }
    }

}