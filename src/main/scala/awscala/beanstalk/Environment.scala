package awscala.beanstalk

import awscala._
import com.amazonaws.services.{ elasticbeanstalk => aws }

import collection.JavaConverters._
import java.util.Date

object Environment {
  def apply(e: aws.model.EnvironmentDescription): Environment =
    Environment(e.getEnvironmentName, e.getEnvironmentId, e.getCNAME, e.getEndpointURL,
      e.getApplicationName, e.getDescription, e.getVersionLabel,
      e.getSolutionStackName, e.getTemplateName, Option(e.getResources).map(r => LoadBalancer(r.getLoadBalancer)).orNull,
      EnvironmentHealth(e.getHealth), EnvironmentStatus(e.getStatus),
      e.getDateCreated, e.getDateUpdated)

  def apply(e: aws.model.CreateEnvironmentResult): Environment =
    Environment(e.getEnvironmentName, e.getEnvironmentId, e.getCNAME, e.getEndpointURL,
      e.getApplicationName, e.getDescription, e.getVersionLabel,
      e.getSolutionStackName, e.getTemplateName,
      Option(e.getResources).map(r => LoadBalancer(r.getLoadBalancer)).orNull,
      EnvironmentHealth(e.getHealth), EnvironmentStatus(e.getStatus),
      e.getDateCreated, e.getDateUpdated)
}

case class Environment(
    //                   underlying: aws.model.EnvironmentDescription
    environmentName: String,
    environmentId: String,
    cname: String,
    url: String,
    applicationName: String,
    description: String,
    versionLabel: String,
    solutionStackName: String,
    templateName: String,
    loadBalancer: LoadBalancer,
    health: EnvironmentHealth,
    status: EnvironmentStatus,
    dateCreated: Date,
    dateUpdated: Date) {

  def Latest()(implicit beanstalk: Beanstalk): Environment = beanstalk.environment(environmentName).getOrElse(throw new RuntimeException(s"Beanstalk Environment ${environmentName} Not Found"))

  def getLoadBalancer: Option[LoadBalancer] = Option(loadBalancer)

  def restart()(implicit beanstalk: Beanstalk): Unit = beanstalk.restart(this)

  def update(confs: Configuration*)(implicit beanstalk: Beanstalk): Unit = beanstalk.update(this, confs: _*)

  def update(versionLabel: String)(implicit beanstalk: Beanstalk): Unit = beanstalk.update(this, versionLabel)

  def rebuild()(implicit beanstalk: Beanstalk): Unit = beanstalk.rebuild(this)

  def swapCNAME(destination: Environment)(implicit beanstalk: Beanstalk): Unit = beanstalk.swapCNAMEs(this, destination)

  def destroy()(implicit beanstalk: Beanstalk): Unit = beanstalk.terminate(this)
}
