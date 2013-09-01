package awscala.beanstalk

import com.amazonaws.services.{ elasticbeanstalk => aws }
import java.util.Date

object UpdateEnvironmentResult {
  def apply(u: aws.model.UpdateEnvironmentResult): UpdateEnvironmentResult =
    UpdateEnvironmentResult(u.getApplicationName,
      u.getDescription,
      u.getVersionLabel,
      LoadBalancer(u.getResources.getLoadBalancer),
      u.getCNAME,
      u.getEndpointURL,
      u.getHealth,
      u.getStatus,
      u.getTemplateName,
      u.getDateCreated,
      u.getDateUpdated)
}

case class UpdateEnvironmentResult(applicationName: String, description: String, versionLabel: String, loadBalancer: LoadBalancer,
  cname: String, endpointUrl: String, health: String, status: String, templateName: String,
  dateCreated: Date, dateUpdated: Date)
