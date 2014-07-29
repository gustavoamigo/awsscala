package com.taxis99.aws

import scala.collection.JavaConversions._

import com.amazonaws.{ Protocol, ClientConfiguration }
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client

trait S3Helper {

  val awsCredential = Configuration.awsCredentials.accessKey

  val awsSecret = Configuration.awsCredentials.secretKey

  lazy val s3 =  {
    val clientConfig = new ClientConfiguration()
    clientConfig.setProtocol(Protocol.HTTP)
    new AmazonS3Client(new BasicAWSCredentials(awsCredential, awsSecret), clientConfig)
  }

  def listFiles(bucketName: String, prefix: String) = {
    s3.listObjects(bucketName, prefix).getObjectSummaries.sortBy(_.getLastModified).reverse
  }

}

