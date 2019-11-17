package lesson3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import kotlin.test.Test

class JavaHeadSetTest : AbstractHeadTailTest() {

    private fun <T : Comparable<T>> createJavaTree(): CheckableSortedSet<T> = BinaryTree()

    @BeforeEach
    fun fillTree() {
        fillTree { BinaryTree() }
    }

    @BeforeEach
    fun fillMyTree() {
        fillMyTree { BinaryTree() }
    }

    @Test
    @Tag("Normal")
    fun headSetTest() {
        doHeadSetTest()
    }

    @Test
    @Tag("Hard")
    fun headSetRelationTest() {
        doHeadSetRelationTest()
    }

    @Test
    @Tag("Normal")
    fun tailSetTest() {
        doTailSetTest()
    }

    @Test
    @Tag("Hard")
    fun tailSetRelationTest() {
        doTailSetRelationTest()
    }

    @Test
    @Tag("Impossible")
    fun subSetTest() {
        doSubSetTest()
        doSubSetRelationTest()
    }

    @Test
    @Tag("Impossible")
    fun subSetIteratorTest() {
        testSubBinaryTreeIterator { createJavaTree() }
        testSubBinaryTreeIteratorRemove { createJavaTree() }
    }
}