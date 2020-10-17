package genetikt

import genetikt.chromosome.IChromosome
import java.lang.StringBuilder
import java.util.*

/**
 * Individual of a population, it is made of a set of chromosomes.
 *
 * @constructor
 *    Creates an individual from a set of particular chromosomes.
 * @param chromosomes
 *    Chromosomes that make the genotype of the individual.
 * @param  mutationRate
 *    Mutation rate of the genes of the individual.
 * @param fitnessFunction
 *    **(Optional)** Custom fitness function.
 * @param filterFunction
 *    **(Optional)**
 *    Custom filter function.
 *    By default, there's no filter for the individuals.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.2
 */
class Individual(
    vararg chromosomes: IChromosome<*>,
    private val mutationRate: Double,
    private val fitnessFunction: ((Individual) -> DoubleArray)? = null,
    filterFunction: ((Individual) -> Unit)? = null
) : Comparable<Individual> {

//region Properties
  /** Fitness of the individual. */
  var fitness: DoubleArray = doubleArrayOf(0.0)

  /** Number of chromosomes of the individual. */
  internal val size: Int = chromosomes.size

  /** Array of chromosomes of the individual. */
  var genotype = Array(chromosomes.size) { i -> chromosomes[i] }
    private set

  /** Provides a way of filtering the selected individuals. */
  private val filter: (Individual) -> Unit
//endregion

  init {
    filter = filterFunction ?: { individual: Individual -> defaultFilter(individual) }
    filter(this)
    updateFitness()
  }

//region Genetic functions
  /** Generates an offspring from 2 parents. */
  internal fun crossover(other: Individual, mixingPoint: Int): Individual {
    assert(size == other.size) {
      "Size of the individuals doesn't match. Can't do a crossover."
    }
    val offspringGenotype = Array(size) { i ->
      crossover(genotype[i], other.genotype[i], mixingPoint)
    }
    return Individual(
        *offspringGenotype,
        mutationRate = mutationRate,
        fitnessFunction = fitnessFunction,
        filterFunction = filter
    )
  }

  /** Generates a new chromosome by doing a crossover of two parents. */
  private fun crossover(
      chrom1: IChromosome<*>,
      chrom2: IChromosome<*>,
      mixingPoint: Int
  ): IChromosome<*> {
    val offspring = chrom1.copy()

    (0 until offspring.size)
        .filter { it > mixingPoint }
        .forEach { chrom2.genes[it].copyTo(offspring.genes[it]) }
    return offspring
  }

  /**
   * Mutates the individual according to it's mutation rate.
   */
  internal fun mutate() {
    for (chromosome in genotype) chromosome.mutate(mutationRate)
    filter(this)
    updateFitness()
  }

  fun replaceGeneAt(chromosomeIndex: Int, geneIndex: Int, with: Any) {
    genotype[chromosomeIndex].replaceGeneAt(geneIndex, with)
    updateFitness()
  }

  /**
   * Changes the fitness of the individual according to a custom fitness function or using the
   * default one.
   */
  private fun updateFitness() {
    fitness = fitnessFunction?.invoke(this) ?: defaultFitness(this)
  }

  /**
   * Calculates the fitness of an individual.
   */
  private fun defaultFitness(individual: Individual): DoubleArray {
    val fit = individual.genotype
        .map { chromosome ->
          (0 until chromosome.target.size)
              .count { chromosome.genes[it] == chromosome.target[it] }
        }
        .map { it.toDouble() }
    return fit.toDoubleArray()
  }

  /**
   * Default filter of the individual. Does nothing.
   */
  @Suppress("UNUSED_PARAMETER")
  private fun defaultFilter(individual: Individual) {
    return
  }
//endregion

//region Utility functions
  /**
   * Compares this object with the specified object for order. Returns zero if this object is equal
   * to the specified [other] object, a negative number if it's less than [other], or a positive number
   * if it's greater than [other].
   */
  override fun compareTo(other: Individual): Int {
    for (i in 0 until fitness.size) {
      return if (fitness[i] == other.fitness[i])
        continue
      else if (fitness[i] > other.fitness[i])
        1
      else
        -1
    }
    return 0
  }

  /**
   * Returns the string representation of the individual.
   */
  override fun toString(): String {
    val sb = StringBuilder()
    for (i in 0 until size) {
      sb.append(genotype[i].toString())
      if (i < size - 1) sb.append(", ")
    }
    return sb.toString()
  }

  /**
   * Returns true if this individual is equal to another, fals if not.
   */
  override fun equals(other: Any?): Boolean {
    if (other !is Individual) return false
    if (other.size != this.size) return false
    return (0 until size).none { genotype[it] != other.genotype[it] }
  }

  /**
   * Returns the hash code of this object.
   */
  override fun hashCode(): Int {
    var result = Arrays.hashCode(fitness)
    result = 31 * result + size
    result = 31 * result + Arrays.hashCode(genotype)
    return result
  }
//endregion
}