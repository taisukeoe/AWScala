package awscala.beanstalk

sealed abstract class EnvironmentBase(name: String)
case class Template(name: String) extends EnvironmentBase(name)
case class SolutionStack(name: String) extends EnvironmentBase(name)
object AmazonLinux64Tomcat7 extends SolutionStack("64bit Amazon Linux running Tomcat 7")
