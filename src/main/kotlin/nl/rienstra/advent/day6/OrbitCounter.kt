package nl.rienstra.advent.day6

object OrbitCounter {
    fun countOrbits(inputs: List<String>, orbitToCount: String): Int = generateOrbits(inputs)[orbitToCount]?.countOrbitsNumberOfParentOrbits()
            ?: 0

    fun countAllOrbits(inputs: List<String>): Int {
        val orbits = generateOrbits(inputs)
        return orbits.keys.map { orbits[it]!!.countOrbitsNumberOfParentOrbits() }.sum()
    }

    fun calculateNumberOfTransfers(inputs: List<String>, from: String, to: String): Int {
        val orbits = generateOrbits(inputs)

        return orbits.values.filter { orbits[from]!!.isParent(it.name) && orbits[to]!!.isParent(it.name) }
                .map { orbits[from]!!.distanceToParent(it.name) + orbits[to]!!.distanceToParent(it.name) }
                .min() ?: -1
    }

    private fun generateOrbits(inputs: List<String>): MutableMap<String, Orbit> {
        val orbits = mutableMapOf<String, Orbit>()

        inputs.forEach { input ->
            val parentOrbitName = input.split(")")[0]
            val childOrbitName = input.split(")")[1]

            val parentOrbit = orbits.getOrPut(parentOrbitName) { Orbit(parentOrbitName, null) }
            if (orbits.containsKey(childOrbitName)) {
                orbits[childOrbitName] = Orbit(childOrbitName, parentOrbit)
                replaceChildren(orbits, childOrbitName)
            } else {
                orbits[childOrbitName] = Orbit(childOrbitName, parentOrbit)
            }
        }
        return orbits
    }

    private fun replaceChildren(orbits: MutableMap<String, Orbit>, childOrbitName: String) {
        val childrenToReplace = orbits.values.filter { it.parentOrbit?.name == childOrbitName }
        childrenToReplace.forEach {
            orbits[it.name] = Orbit(it.name, orbits[childOrbitName])
            replaceChildren(orbits, it.name)
        }
    }
}

data class Orbit(val name: String, val parentOrbit: Orbit?) {
    fun countOrbitsNumberOfParentOrbits(): Int = when {
        parentOrbit == null -> 0
        else -> parentOrbit.countOrbitsNumberOfParentOrbits() + 1
    }

    fun isParent(name: String): Boolean = when {
        parentOrbit == null -> false
        parentOrbit.name == name -> true
        else -> parentOrbit.isParent(name)
    }

    fun distanceToParent(name: String): Int = when {
        parentOrbit == null -> throw IllegalStateException("Not a parent")
        parentOrbit.name == name -> 0
        else -> parentOrbit.distanceToParent(name) + 1
    }
}