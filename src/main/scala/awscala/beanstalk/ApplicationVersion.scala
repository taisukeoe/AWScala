package awscala.beanstalk

import awscala._
import com.amazonaws.services.{ elasticbeanstalk => aws }

import collection.JavaConverters._
import java.util.Date

object ApplicationVersion {
  //  def apply(underlying: aws.model.ApplicationVersionDescription): ApplicationVersion =
  //    ApplicationVersion(underlying.getApplicationName,
  //      underlying.getDescription,
  //      underlying.getVersionLabel,
  //      underlying.getSourceBundle.getS3Bucket,
  //      underlying.getSourceBundle.getS3Key,
  //      underlying.getDateCreated,
  //      underlying.getDateUpdated)

  def apply(underlying: aws.model.ApplicationVersionDescription): ApplicationVersion = ApplicationVersion(underlying)
}

class ApplicationVersion(underlying: aws.model.ApplicationVersionDescription) {
  def applicationName: String = underlying.getApplicationName
  def description: String = underlying.getDescription
  def versionLabel: String = underlying.getVersionLabel
  def sourceS3Bucket: String = underlying.getSourceBundle.getS3Bucket
  def sourceS3Key: String = underlying.getSourceBundle.getS3Key
  def dateCreated: Date = underlying.getDateCreated
  def dateUpdated: Date = underlying.getDateUpdated

  def updateDescription(description: String)(implicit beanstalk: Beanstalk): ApplicationVersion = beanstalk.updateVersionDescription(applicationName, versionLabel, description)

  def createEnvironment(name: String)(implicit beanstalk: Beanstalk): Environment = beanstalk.createEnvironment(this, name)

  def createAndAwaitEnvironment(name: String)(implicit beanstalk: Beanstalk): Environment = beanstalk.createAndAwaitEnvironment(this, name)

  def destroy()(implicit beanstalk: Beanstalk): Unit = beanstalk.delete(this)

  override def toString: String = s"ApplicationVersion ${underlying.toString}"
}
//case class ApplicationVersion(name: String, description: String, versionLabel: String,
//                              sourceS3Bucket: String, sourceS3Key: String,
//                              dateCreated: Date, dateUpdated: Date)
