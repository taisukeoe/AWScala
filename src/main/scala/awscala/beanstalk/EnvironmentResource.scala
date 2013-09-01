package awscala.beanstalk

import awscala._
import com.amazonaws.services.{ elasticbeanstalk => aws }

import collection.JavaConverters._
import java.util.Date

case class EnvironmentResource(underlying: aws.model.EnvironmentResourceDescription) {
  def loadBalances: Seq[String] = underlying.getLoadBalancers.asScala.map(_.getName)
  def triggers: Seq[String] = underlying.getTriggers.asScala.map(_.getName)
  def autoScalingGroups: Seq[String] = underlying.getAutoScalingGroups.asScala.map(_.getName)
  def environmentName: String = underlying.getEnvironmentName
  def instanceIds: Seq[String] = underlying.getInstances.asScala.map(_.getId)
  def launchConfigurations: Seq[String] = underlying.getLaunchConfigurations.asScala.map(_.getName)
}
