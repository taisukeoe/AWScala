package awscala.beanstalk

import com.amazonaws.services.{ elasticbeanstalk => aws }

sealed abstract class EnvironmentHealth
case object Green extends EnvironmentHealth
case object Yellow extends EnvironmentHealth
case object Grey extends EnvironmentHealth
case object Red extends EnvironmentHealth

object EnvironmentHealth {
  def apply(e: aws.model.EnvironmentStatus): EnvironmentHealth = apply(e.toString)
  def apply(name: String): EnvironmentHealth = name match {
    case "Green" => Green
    case "Yellow" => Yellow
    case "Grey" => Grey
    case "Red" => Red
    case _ => throw new RuntimeException(s"Unknown Environment Health:${name}")
  }
}

sealed abstract class EnvironmentStatus
case object Launching extends EnvironmentStatus
case object Updating extends EnvironmentStatus
case object Ready extends EnvironmentStatus
case object Terminating extends EnvironmentStatus
case object Terminated extends EnvironmentStatus

object EnvironmentStatus {
  def apply(e: aws.model.EnvironmentStatus): EnvironmentStatus = apply(e.toString)
  def apply(name: String): EnvironmentStatus = name match {
    case "Launching" => Launching
    case "Updating" => Updating
    case "Ready" => Ready
    case "Terminating" => Terminating
    case "Terminated" => Terminated
    case _ => throw new RuntimeException(s"Unknown Environment Status:${name}")
  }
}

