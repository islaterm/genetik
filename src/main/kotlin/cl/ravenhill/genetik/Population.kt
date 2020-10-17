package genetikt

import java.util.*

/**
 * A population es a set of individuals.
 *
 * @param size
 *    Number of individuals of the population.
 * @param individualFactory
 *    Factory from which the individuals are going to be created.
 * @constructor
 *    Creates a population of individuals using a factory.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.2
 */
class Population(
    size: Int,
    individualFactory: IndividualFactory
) {

  private val rand = Random()

  /**
   * List of the individuals that make the population.
   * The list is always sorted from lower to higher fitness.
   */
  private var individuals = mutableListOf<Individual>()

  init {
    for (i in 0 until size) {
      val ind = individualFactory.build()
      individuals.add(ind)
    }
    individuals.sort()
  }

  /**
   * Evolves the population to a next generation.
   */
  fun evolve() {
    val childrens = mutableListOf<Individual>()
    val survivors = individuals.size / 4
    var i = 0
    while (childrens.size < individuals.size)
      if (i < individuals.size - survivors - 1) {
        val parent1 = tournamentSelection()
        val parent2 = tournamentSelection()
        val mixingPoint = rand.nextInt(parent1.size)

        val child1 = parent1.crossover(parent2, mixingPoint)
        child1.mutate()
        childrens.add(child1)

        val child2 = parent2.crossover(parent1, mixingPoint)
        child2.mutate()
        childrens.add(child2)

        i += 2
      } else
        childrens.add(individuals[i++])
    individuals = childrens
    individuals.sort()
  }

  /** Returns the fittest individual. */
  fun getFittest() = individuals.last()

  //region Private functions

  /** Selects a random individual prioritizing the ones with greater fitness. */
  private fun tournamentSelection(): Individual {
    val index = maxOf(rand.nextInt(individuals.size), rand.nextInt(individuals.size))
    // The individual with higher index has higher fitness.
    return individuals[index]
  }
  //endregion
}