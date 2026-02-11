import java.nio.file.{Files, Path}
import java.time.{Instant, LocalDateTime, ZoneOffset}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable
import scala.jdk.CollectionConverters.*


class MovieData(val id: Long, val title: String, val year: Option[Int], val tags: ArraySeq[String])


class RatingData(val userId: Long, val movieId: Long, val rating: Double, val timestamp: LocalDateTime)


class MovieDataset(basePath: Path) {

  // ignore this
  private def parseMovie(line: String): MovieData = {
    val firstComma = line.indexOf(',')
    val lastComma = line.lastIndexOf(',')
    val id = line.substring(0, firstComma).toLong
    val rawTitle = line.substring(firstComma + 1, lastComma).stripPrefix("\"").stripSuffix("\"")
    val year = "\\([0-9]{4}\\)".r.findFirstIn(rawTitle).map(_.substring(1, 5).toInt)
    val title = rawTitle.replaceFirst(" *\\([0-9]{4}\\) *", "")
    val rawTags = line.substring(lastComma + 1).trim
    val tags = if (rawTags == "(no genres listed)") ArraySeq.empty[String] else rawTags.split("\\|").to(ArraySeq)
    MovieData(id, title, year, tags)
  }

  // ignore this
  private def parseRating(line: String): RatingData = {
    line.strip.split(" *, *") match {
      case Array(rawUserId, rawMovieId, rawRating, rawTimestamp) =>
        val normalizedTime = Instant.ofEpochSecond(rawTimestamp.toLong).atOffset(ZoneOffset.UTC).toLocalDateTime
        RatingData(rawUserId.toLong, rawMovieId.toLong, rawRating.toDouble, normalizedTime)
      case _ => throw new IllegalArgumentException("bad format")
    }
  }

  /** A collection of movies in the dataset. */
  val movies: ArraySeq[MovieData] =
    Files.lines(basePath.resolve("movies.csv"))
      .iterator
      .asScala
      .drop(1)
      .filter(line => line.nonEmpty)
      .map(parseMovie)
      .to(ArraySeq)

  /** A collection of user ratings referencing the movies in the dataset. */
  val ratings: ArraySeq[RatingData] =
    Files.lines(basePath.resolve("ratings.csv"))
      .iterator
      .asScala
      .drop(1)
      .filter(line => line.nonEmpty)
      .map(parseRating)
      .to(ArraySeq)

  // consistency check
  if (ratings.map(m => m.movieId).toSet.diff(movies.map(m => m.id).toSet).nonEmpty)
    throw new IllegalStateException("ratings contain invalid movie ids")

  /** Returns all movies containing the specified search term (case-insensitive). */
  def findMoviesWithName(term: String): Seq[MovieData] = ???

  /** Returns the movie with the specified id, or None if not found. */
  def findMovieById(movieId: Long): Option[MovieData] = ???

  /** Returns all ratings for the specified movie id. */
  def ratingsForMovie(movieId: Long): Seq[RatingData] = ???

  /** Returns all movies that have the specified tag. */
  def findMoviesWithTag(tag: String): Seq[MovieData] = ???

  /** Returns all movies with missing year information. */
  def missingYear: Seq[MovieData] = ???

  /** Returns all possible tags in the dataset. */
  def allTags: Set[String] = ???

  /** Returns all movies that have not been rated by any user. */
  def unratedMovies: Seq[MovieData] = ???

  /** Returns all movies sorted by year, where movies from the same year are sorted alphabetically. */
  def moviesChronologically: Seq[MovieData] = ???

  /** Returns the average rating for the specified movie id, or None if the movie has no ratings.
   *
   * @param movieId the movie id
   * @return the average rating, or None if there are no ratings
   * @throws IllegalArgumentException if the movie id is not found in the dataset
   */
  def queryAverageRating(movieId: Long): Option[Double] = ???

  /** Returns the top movies with at least `minRatings` ratings, sorted descendingly by average rating.
   *
   * @param count the number of top movies to return
   * @param minRatings the minimum number of ratings a movie must have to be considered
   * @return a sequence of (`MovieData`, rating count, average rating) tuples
   */
  def queryTopMovies(count: Int, minRatings: Int): Seq[(MovieData, Int, Double)] = ???
}


@main def main(): Unit = {
  val data = MovieDataset(Path.of("datasets/movielens-reduced"))

  println(data.findMoviesWithName("spider-man"))
  println(data.queryAverageRating(5349))
  println(data.queryTopMovies(10, 100))
}
