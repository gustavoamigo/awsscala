package com.taxis99.aws

import scala.collection.JavaConversions._

import com.amazonaws.{ Protocol, ClientConfiguration }
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client

/**
 * Helper to handle S3 Interface
 */
class S3Helper(accessKey: String, secretKey: String, bucketName: String) {

  def createClient(): AmazonS3Client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey))

  lazy val client = createClient()

  def listFiles(prefix: String) = {
    client.listObjects(bucketName, prefix).getObjectSummaries.sortBy(_.getLastModified).reverse
  }

}

