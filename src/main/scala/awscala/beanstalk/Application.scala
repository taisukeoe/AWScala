package awscala.beanstalk

import awscala._
import com.amazonaws.services.{ elasticbeanstalk => aws }

import collection.JavaConverters._
import java.util.Date

object Application {
  def apply(ad: aws.model.ApplicationDescription) = new Application(ad)
}
class Application(underlying: aws.model.ApplicationDescription) {
  def name: String = underlying.getApplicationName

  def configurationTemplates: Seq[String] = underlying.getConfigurationTemplates.asScala

  def dateCreated: Date = underlying.getDateCreated
  def dateUpdated: Date = underlying.getDateUpdated
  def description: String = underlying.getDescription

  def versions: Seq[String] = underlying.getVersions.asScala

  def environments()(implicit beanstalk: Beanstalk): Seq[Environment] = beanstalk.environments(this)

  def createVersion(version: String, s3obj: s3.S3Object)(implicit beanstalk: Beanstalk): ApplicationVersion = beanstalk.createVersion(this, version, s3obj)

  def createEnvironment(name: String)(implicit beanstalk: Beanstalk): Environment = beanstalk.createEnvironment(this, name)

  def createAndAwaitEnvironment(name: String)(implicit beanstalk: Beanstalk): Environment = beanstalk.createEnvironment(this, name)

  def destroy()(implicit beanstalk: Beanstalk): Unit = beanstalk.delete(this)

  override def toString: String = s"Application ${underlying.toString}"
}
