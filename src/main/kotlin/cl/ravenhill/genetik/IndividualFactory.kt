package genetikt

import genetikt.chromosome.IChromosomeFactory

/**
 * @constructor
 *    Creates a factory for building individuals.
 * @param factories
 *    Factories for creating chromosomes.
 * @param mutationRate
 *    Mutation rate of the individual.
 * @param fitnessFunction
 *    **(Optional)**
 *    Fitness function of the individual.
 * @param filterFunction
 *    **(Optional)**
 *    Filter function of the individual.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.2
 */
class IndividualFactory(
    private vararg val factories: IChromosomeFactory<*>,
    private val mutationRate: Double,
    private val fitnessFunction: ((Individual) -> DoubleArray)? = null,
    private val filterFunction: ((Individual) -> Unit)? = null
) {

  /** Builds an individual. */
  fun build(): Individual {
    val genotype = Array(factories.size) { i -> factories[i].build() }
    return Individual(*genotype, mutationRate = mutationRate, fitnessFunction = fitnessFunction,
        filterFunction = filterFunction)
  }
}