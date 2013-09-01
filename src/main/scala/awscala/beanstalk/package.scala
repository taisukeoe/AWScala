package awscala

import com.amazonaws.services.{ elasticbeanstalk => aws }

package object beanstalk {
  type Configuration = aws.model.ConfigurationOptionSetting
}
