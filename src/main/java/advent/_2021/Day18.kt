package advent._2021

import advent.util.ADay
import advent.util.Day

data class SNumber(var iniLeft: Any, var iniRight: Any) {

    var parent: SNumber? = null
    var parents = 0
        set(value) {
            field = value
            updateChilds()
        }

    var left: Any = iniLeft
        set(value) {
            field = value
            (value as? SNumber)?.parent = this
            updateChilds()
        }

    var right: Any = iniRight
        set(value) {
            field = value
            (value as? SNumber)?.parent = this
            updateChilds()
        }

    init {
        updateChilds()
        (left as? SNumber)?.parent = this
        (right as? SNumber)?.parent = this
    }

    private fun updateChilds() {
        (left as? SNumber)?.parents = parents + 1
        (right as? SNumber)?.parents = parents + 1
    }

    operator fun plus(snumber: SNumber) = SNumber(this, snumber)

    fun addUpLeft(nr: Int) {
        val p = parent ?: return
        val pL = p.left
        if (pL == this) {
            p.addUpLeft(nr)
        } else if (pL is Int) {
            p.left = pL + nr
        } else if (pL is SNumber) {
            pL.addDownRight(nr)
        }
    }

    fun addUpRight(nr: Int) {
        val p = parent ?: return
        val pR = p.right
        if (pR == this) {
            p.addUpRight(nr)
        } else if (pR is Int) {
            p.right = pR + nr
        } else if (pR is SNumber) {
            pR.addDownLeft(nr)
        }
    }

    fun addDownRight(nr: Int) {
        val r = right ?: return
        if (r is Int) {
            right = r + nr
        } else if (r is SNumber) {
            r.addDownRight(nr)
        }
    }

    fun addDownLeft(nr: Int) {
        val l = left ?: return
        if (l is Int) {
            left = l + nr
        } else if (l is SNumber) {
            l.addDownLeft(nr)
        }
    }

    fun explode(): Boolean {
        val l = left as? Int
        val r = right as? Int

        if (l == null || r == null) {
            if ((left as? SNumber)?.explode() == true) return true
            if ((right as? SNumber)?.explode() == true) return true
            return false
        }

        if (parents < 4) return false

        addUpLeft(l)
        addUpRight(r)

        if (parent?.left == this) {
            parent?.left = 0
        } else if (parent?.right == this) {
            parent?.right = 0
        }

        return true
    }

    fun split(): Boolean {
        val l = left as? Int
        val r = right as? Int

        if ((left as? SNumber)?.split() == true) return true
        if (l != null && l > 9) {
            left = SNumber(Math.floor(l.toDouble() / 2).toInt(), Math.ceil(l.toDouble() / 2).toInt())
            return true
        }


        if ((right as? SNumber)?.split() == true) return true
        if (r != null && r > 9) {
            right = SNumber(Math.floor(r.toDouble() / 2).toInt(), Math.ceil(r.toDouble() / 2).toInt())
            return true
        }

        return false
    }

    fun magnitude(): Long {
        var result = 0L
        (left as? Int)?.let { result += (3 * it) }
        (left as? SNumber)?.let { result += 3 * it.magnitude() }
        (right as? Int)?.let { result += (2 * it) }
        (right as? SNumber)?.let { result += 2 * it.magnitude() }
        return result
    }

    override fun toString(): String {
        return "[$left,$right]" + ":$parents"
    }
    // SHOULD BE [[[[7,7],[7,7]],[[7,0],[7,7]]],[[[7,7],[6,7]],[[7,7],[8,9]]]]
    //        IS [[[[7,7],[7,7]],[[7,7],[0,7]]],[[[7,7],[6,7]],[[9,5],[8,0]]]]
}

// TODO does not work yet somehow
@ADay(2021, 18, "day18")
class Day18 : Day(false) {

    override fun doPart1(input: List<String>): Any {
        var tree = parse(input.first().iterator(), false) as SNumber
        log(tree.toString())
        input.asSequence().drop(1).forEach {
            val addTree: SNumber = parse(it.iterator(), false) as SNumber
            tree += addTree
            log("added: $tree")
            while (true) {
                if (tree.explode()) {
                    log("explo: $tree")
                    continue
                }
                if (tree.split()) {
                    log("split: $tree")
                    continue
                }
                break
            }
            log("compl: $tree")
        }
        return tree.magnitude()
    }

    override fun doPart2(input: List<String>): Any {
        return -1
    }

    private fun parse(input: CharIterator, skipFirst: Boolean = true): Any {
        if (skipFirst.not()) input.nextChar()
        val first = input.nextChar()
        val left = when (first) {
            '[' -> parse(input)
            else -> Character.getNumericValue(first)
        }

        val comma = input.nextChar()
        if (comma == ']') return left

        val firstRight = input.nextChar()
        val right = when (firstRight) {
            '[' -> parse(input)
            else -> Character.getNumericValue(firstRight)
        }
        val ending = input.nextChar()
        return SNumber(left, right)
    }
}
