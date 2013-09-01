package awscala.beanstalk

import awscala._
import com.amazonaws.services.{ elasticbeanstalk => aws }

package environment {
  case class HealthCheckInterval(sec: Int) extends aws.model.ConfigurationOptionSetting("aws:elb:healthcheck", "Interval", sec.toString)

  case class HealthCheckURL(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:application", "Application Healthcheck URL", value)

  case class HealthCheckTimeout(sec: Int) extends aws.model.ConfigurationOptionSetting("aws:elb:healthcheck", "Timeout", sec.toString)

  case class HealthyThreshold(count: Int) extends aws.model.ConfigurationOptionSetting("aws:elb:healthcheck", "HealthyThreshold", count.toString)

  case class UnhealthyThreshold(count: Int) extends aws.model.ConfigurationOptionSetting("aws:elb:healthcheck", "UnhealthyThreshold", count.toString)

  case class JDBC(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:application:environment", "JDBC_CONNECTION_STRING", value)

  case class PARAM1(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:application:environment", "PARAM1", value)

  case class PARAM2(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:application:environment", "PARAM2", value)

  case class PARAM3(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:application:environment", "PARAM3", value)

  case class PARAM4(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:application:environment", "PARAM4", value)

  case class PARAM5(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:application:environment", "PARAM5", value)

  case class AccessKey(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:application:environment", "AWS_ACCESS_KEY_ID", value)

  case class SecretKey(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:application:environment", "AWS_SECRET_KEY", value)
}

package container {

  case class MaxPermSize(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:container:tomcat:jvmoptions", "XX:MaxPermSize", value)

  case class Xmx(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:container:tomcat:jvmoptions", "Xmx", value)

  case class Xms(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:container:tomcat:jvmoptions", "Xms", value)

}

package sns {

  case class NotificationTopic(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:sns:topics", "Notification Topic Name", value)

  case class NotificationProtocol(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:sns:topics", "Notification Protocol", value)

  case class NotificationARN(value: String) extends aws.model.ConfigurationOptionSetting("aws:elasticbeanstalk:sns:topics", "Notification Topic ARN", value)
}

package autoscaling {

  case class EC2KeyName(value: String) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:launchconfiguration", "EC2KeyName", value)

  case class InstanceType(instanceType: ec2.InstanceType) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:launchconfiguration", "instanceType", instanceType.toString)

  case class ImageId(value: String) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:launchconfiguration", "imageId", value)

  case class SecurityGroup(value: String) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:launchconfiguration", "SecurityGroups", value)

  case class BreachDuration(min: Int) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:trigger", "BreachDuration", min.toString)

  case class UpperThreshold(num: Long) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:trigger", "UpperThreshold", num.toString)

  case class LowerThreshold(num: Long) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:trigger", "LowerThreshold", num.toString)

  case class LowerScaleBreachIncrement(num: Int) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:trigger", "LowerBreachScaleIncrement", if (num < 0) num.toString else (-num).toString)

  case class UpperScaleBreachIncrement(num: Int) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:trigger", "UpperBreachScaleIncrement", num.toString)

  case class Statistic(name: TriggerStatistic.Value) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:trigger", "Statistic", name.toString)

  case class MeasureName(name: TriggerMeasurement.Value) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:trigger", "MeasureName", name.toString)

  case class MeasureUnit(unit: MeasurementUnit.Value) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:trigger", "Unit", unit.toString)

  case class Period(min: Int) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:trigger", "Period", min.toString)

  case class Cooldown(sec: Int) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:asg", "Cooldown", sec.toString)

  case class MaxSize(num: Int) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:asg", "MaxSize", num.toString)

  case class MinSize(num: Int) extends aws.model.ConfigurationOptionSetting("aws:autoscaling:asg", "MinSize", num.toString)

  object TriggerMeasurement extends Enumeration {
    val CPUUtilization, NetworkIn, NetworkOut, DiskWriteOpts, DiskReadBytes, DiskReadOps, DiskWriteBytes, Latency, RequestCount, HealthyHostCount, UnhealthyHostCount = Value
  }

  object TriggerStatistic extends Enumeration {
    val Minimum, Maximum, Sum, Average = Value
  }

  object MeasurementUnit extends Enumeration {
    val Seconds, Percent, Bytes, Bits, Count = Value
    val BytesPerSecond = Value("Bytes/Second")
    val BitsPerSeconds = Value("Bits/Second")
    val NoUnit = Value("None")
    val CountPerSecond = Value("Count/Second")
  }

}
