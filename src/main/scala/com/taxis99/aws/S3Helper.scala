package com.taxis99.aws

import com.amazonaws.{ Protocol, ClientConfiguration }
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client

trait S3Helper {

  lazy val s3 =  {
    val clientConfig = new ClientConfiguration()
    clientConfig.setProtocol(Protocol.HTTP)

    new AmazonS3Client(
      new BasicAWSCredentials(Configuration.awsCredentials.accessKey, Configuration.awsCredentials.secretKey),
      clientConfig)
  }

}
