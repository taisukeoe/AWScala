package awscala

import awscala._, beanstalk._

import org.slf4j._
import org.scalatest._
import org.scalatest.matchers._

import java.io._

class BeanstalkSpec extends FlatSpec with ShouldMatchers {

  behavior of "Beanstalk"

  val log = LoggerFactory.getLogger(this.getClass)

  it should "provide cool APIs" in {
    implicit val beanstalk = Beanstalk.at(Region.Tokyo)

    val appName = s"awscala-${System.currentTimeMillis}"
    val envName = s"awscala-${System.currentTimeMillis}"

    //create Application
    val application = beanstalk.createApplication(appName, appName)

    log.info(s"Created BeanstalkApplication ${application.name}")

    //create Environment

    val env = application.createEnvironment(envName)
    log.info(s"Created BeanstalkEnvironment ${env.environmentName}")

    while (env.Latest.status != Ready) {
      Thread.sleep(3000L)
    }

    env.destroy

    application.destroy
  }
}
