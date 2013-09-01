package awscala.beanstalk

import awscala._
import com.amazonaws.services.{ elasticbeanstalk => aws }

import collection.JavaConverters._
import awscala.s3.S3Object

object Beanstalk {

  def apply(credentials: Credentials = Credentials.defaultEnv): Beanstalk = new BeanstalkClient(credentials)

  def apply(accessKeyId: String, secretAccessKey: String): Beanstalk = apply(Credentials(accessKeyId, secretAccessKey))

  def at(region: Region): Beanstalk = apply().at(region)
}

trait Beanstalk extends aws.AWSElasticBeanstalk {

  def at(region: Region): Beanstalk = {
    this.setRegion(region)
    this
  }

  // ------------------------------------------
  // Application
  // ------------------------------------------

  def applications: Seq[Application] = describeApplications().getApplications.asScala.map(Application(_))

  def createApplication(name: String, description: String): Application = Application(createApplication(new aws.model.CreateApplicationRequest().withApplicationName(name).withDescription(description)).getApplication)

  def delete(application: Application): Unit = deleteApplication(application.name)

  def deleteApplication(name: String): Unit = deleteApplication(new aws.model.DeleteApplicationRequest().withApplicationName(name))

  // ------------------------------------------
  // Application Version
  // ------------------------------------------

  def versions(): Seq[ApplicationVersion] = describeApplicationVersions().getApplicationVersions.asScala.map(ApplicationVersion(_))

  def versions(application: Application): Seq[ApplicationVersion] = describeApplicationVersions(new aws.model.DescribeApplicationVersionsRequest().withApplicationName(application.name)).getApplicationVersions.asScala.map(ApplicationVersion(_))

  def createVersion(applicationName: String, version: String): ApplicationVersion =
    ApplicationVersion(createApplicationVersion(new aws.model.CreateApplicationVersionRequest()
      .withApplicationName(applicationName)
      .withVersionLabel(version)
    ).getApplicationVersion)

  def createVersion(applicationName: String, version: String, s3bucket: String, s3Key: String): ApplicationVersion =
    ApplicationVersion(createApplicationVersion(new aws.model.CreateApplicationVersionRequest()
      .withApplicationName(applicationName)
      .withVersionLabel(version)
      .withSourceBundle(new aws.model.S3Location().withS3Bucket(s3bucket).withS3Bucket(s3Key))).getApplicationVersion)

  def createVersion(application: Application, version: String, s3obj: S3Object): ApplicationVersion = createVersion(application.name, version, s3obj.getBucketName, s3obj.getKey)

  def updateVersionDescription(applicationName: String, version: String, description: String): ApplicationVersion =
    ApplicationVersion(updateApplicationVersion(new aws.model.UpdateApplicationVersionRequest()
      .withApplicationName(applicationName)
      .withVersionLabel(version)
      .withDescription(description)).getApplicationVersion)

  def updateVersionDescription(application: Application, version: String, description: String): ApplicationVersion = updateVersionDescription(application.name, version, description)

  def delete(applicationVersion: ApplicationVersion, deleteS3: Boolean = false): Unit = deleteVersion(applicationVersion.applicationName, applicationVersion.versionLabel, deleteS3)

  def deleteVersion(name: String, version: String, deleteS3: Boolean = false): Unit = deleteApplicationVersion(new aws.model.DeleteApplicationVersionRequest().withApplicationName(name).withVersionLabel(version).withDeleteSourceBundle(deleteS3))

  // ------------------------------------------
  // Environment
  // ------------------------------------------

  protected def environmentRaw(name: String): Seq[aws.model.EnvironmentDescription] = describeEnvironments(new aws.model.DescribeEnvironmentsRequest().withEnvironmentNames(name)).getEnvironments.asScala

  def environment(name: String): Option[Environment] = environmentRaw(name).headOption.map(Environment(_))

  def environments: Seq[Environment] = describeEnvironments().getEnvironments.asScala.map(Environment(_))

  def environmentStatus(environmentName: String): Option[EnvironmentStatus] = environmentRaw(environmentName).headOption.map(e => EnvironmentStatus(e.getStatus))

  def environmentHealth(environmentName: String): Option[EnvironmentHealth] = environmentRaw(environmentName).headOption.map(e => EnvironmentHealth(e.getHealth))

  def environments(application: Application): Seq[Environment] = describeEnvironments(new aws.model.DescribeEnvironmentsRequest().withApplicationName(application.name)).getEnvironments.asScala.map(Environment(_))

  def createEnvironment(application: Application,
    environmentName: String): Environment =
    Environment(createEnvironment(
      new aws.model.CreateEnvironmentRequest().withApplicationName(application.name)
        .withEnvironmentName(environmentName)
        .withSolutionStackName("64bit Amazon Linux running Tomcat 7")
    ))

  def createEnvironment(applicationVersion: ApplicationVersion,
    environmentName: String): Environment =
    Environment(createEnvironment(
      new aws.model.CreateEnvironmentRequest().withApplicationName(applicationVersion.applicationName)
        .withVersionLabel(applicationVersion.versionLabel)
        .withEnvironmentName(environmentName)
        .withSolutionStackName("64bit Amazon Linux running Tomcat 7")
    ))

  def createEnvironment(applicationName: String,
    versionLabel: String,
    environmentName: String,
    cnamePrefix: String,
    solutionStackName: String = "64bit Amazon Linux running Tomcat 7",
    template: String = null,
    confs: Seq[Configuration]): Environment =
    Environment(createEnvironment(
      new aws.model.CreateEnvironmentRequest().withApplicationName(applicationName)
        .withVersionLabel(versionLabel)
        .withEnvironmentName(environmentName)
        .withCNAMEPrefix(cnamePrefix)
        .withSolutionStackName(solutionStackName)
        .withTemplateName(template)
        .withOptionSettings(confs.asJava)))

  def restart(env: Environment): Unit = restartEnvironment(env.environmentName)

  def restartEnvironment(name: String): Unit = restartAppServer(new aws.model.RestartAppServerRequest().withEnvironmentName(name))

  def rebuild(env: Environment): Unit = rebuildEnvironment(env.environmentName)

  def rebuildEnvironment(name: String): Unit = rebuildEnvironment(new aws.model.RebuildEnvironmentRequest().withEnvironmentName(name))

  def swapCNAMEs(source: Environment, destination: Environment): Unit = swapCNAMEs(source.environmentName, destination.environmentName)

  def swapCNAMEs(sourceEnvironmentName: String, destinationEnvironmentName: String): Unit = swapEnvironmentCNAMEs(new aws.model.SwapEnvironmentCNAMEsRequest().withSourceEnvironmentName(sourceEnvironmentName).withDestinationEnvironmentName(destinationEnvironmentName))

  // This cannot update application version and configuration together.
  def update(environment: Environment, confs: Configuration*): UpdateEnvironmentResult = updateEnvironment(environment.environmentName, null, confs: _*)

  def update(environment: Environment, versionLabel: String): UpdateEnvironmentResult = updateEnvironment(environment.environmentName, versionLabel)

  def updateEnvironment(environmentName: String, versionLabel: String, confs: Configuration*): UpdateEnvironmentResult =
    UpdateEnvironmentResult(updateEnvironment(
      new aws.model.UpdateEnvironmentRequest().withEnvironmentName(environmentName).withVersionLabel(versionLabel).withOptionSettings(confs: _*)))

  def terminate(environment: Environment): Unit = terminateEnvironment(new aws.model.TerminateEnvironmentRequest().withEnvironmentName(environment.environmentName))

  def terminateEnvironment(name: String): Unit = terminateEnvironment(new aws.model.TerminateEnvironmentRequest().withEnvironmentName(name))
}

/**
 * Default Implementation
 *
 * @param credentials credentials
 */
class BeanstalkClient(credentials: Credentials = Credentials.defaultEnv)
  extends aws.AWSElasticBeanstalkClient(credentials)
  with Beanstalk

