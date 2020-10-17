package genetikt.chromosome

import genetikt.gene.TypedGene
import java.lang.StringBuilder
import java.util.*

/**
 * Chromosome that represents a sequence of objects.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.2
 * @version 1.2
 */
class TypedChromosome<DNA> : IChromosome<TypedGene<DNA>> {

  /** Set of valid objects for the genes of the chromosome. */
  private val alphabet: Map<String, DNA>

  /** Genes that make up the chromosome. */
  override val genes: Array<TypedGene<DNA>>

  override fun replaceGeneAt(geneIndex: Int, value: Any) {
    genes[geneIndex].replaceWith(value as String)
  }

  /** Target sequence of the chromosome. */
  override val target: Array<TypedGene<DNA>>

//region Constructors
  /**
   * Create a new chromosome of the given `size` with the defined `alphabet`.
   *
   * @param size
   *    The number of genes of the chromosome.
   * @param target
   *    **(Optional)**
   *    The target sequence of the chromosome.
   *    By default the chromosome has no target.
   * @param alphabet
   *    A dictionary containing the valid set of objects that can be stored in the genes.
   *    The dictionary must be of the type `Map<Key: String, Value: Object>`.
   */
  constructor(
      size: Int,
      target: Array<TypedGene<DNA>>? = null,
      alphabet: Map<String, DNA>
  ) : this(size, target, alphabet, null)

  /**
   * Secondary constructor.
   * Create a new chromosome from a given `genes` array.
   */
  private constructor(
      size: Int,
      target: Array<TypedGene<DNA>>?,
      alphabet: Map<String, DNA>,
      genes: Array<TypedGene<DNA>>?
  ) {
    this.alphabet = alphabet
    this.genes =
        if (genes != null) Array(size) { i -> genes[i] }
        else Array(size) { TypedGene(alphabet) }
    this.target = target ?: Array(0) { TypedGene(alphabet) }
  }
//endregion

  override fun mutate(mutationRate: Double) {
    val rand = Random()
    for (i in 0 until size)
      if (rand.nextDouble() < mutationRate) genes[i] = TypedGene(alphabet)
  }

//region Utility functions.

  override fun copy(): IChromosome<TypedGene<DNA>> {
    val genesCopy = Array(genes.size) { i -> genes[i].copy() }
    return TypedChromosome(size, target, alphabet, genesCopy)
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?): Boolean {
    if (other !is TypedChromosome<*>) return false
    if (other.size != this.size) return false
    return (0 until size).none { genes[it] != other.genes[it] }
  }

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode(): Int {
    var result = alphabet.hashCode()
    result = 31 * result + Arrays.hashCode(genes)
    result = 31 * result + Arrays.hashCode(target)
    return result
  }

  /**
   * Returns the string representation of this chromosome.
   */
  override fun toString(): String {
    return toString(genes)
  }

  /**
   * Returns the string representation of an array of genes.
   */
  private fun toString(genes: Array<TypedGene<DNA>>): String {
    val sb = StringBuilder()
    for (gene in genes) sb.append(gene.toString())
    return sb.toString()
  }
//endregion
}