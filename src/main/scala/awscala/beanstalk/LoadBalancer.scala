package awscala.beanstalk

import awscala._
import com.amazonaws.services.{ elasticbeanstalk => aws }

import collection.JavaConverters._

case class LoadBalancer(underlying: aws.model.LoadBalancerDescription) {
  def domain: String = underlying.getDomain
  def listeners: Seq[Listener] = underlying.getListeners.asScala.map(Listener(_))
  def name: String = underlying.getLoadBalancerName
}

object Listener {
  def apply(l: aws.model.Listener): Listener = Listener(l.getPort, l.getProtocol)
}
case class Listener(port: Int, protocol: String) extends aws.model.Listener {
  setPort(port)
  setProtocol(protocol)
}
