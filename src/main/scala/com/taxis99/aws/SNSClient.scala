package com.taxis99.aws

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.sns.model.{CreateTopicRequest, PublishRequest}
import com.amazonaws.services.sns.{AmazonSNS, AmazonSNSAsyncClient}

class SNSClient (accessKey: String, secretKey: String, topicName: String, endpoint: String){

  def createClient(): AmazonSNS = new AmazonSNSAsyncClient(new BasicAWSCredentials(accessKey, secretKey))

  private lazy val (client, topicArn) = {
    val newClient = createClient()
    newClient.setEndpoint(endpoint)
    (newClient, newClient.createTopic(new CreateTopicRequest(topicName)).getTopicArn)
  }

  def publish(message: String): String = publishWithSubject(message)

  def publishWithSubject(message: String, subject: String = null): String = {
    val result = client.publish(new PublishRequest()
      .withTopicArn(topicArn)
      .withSubject(subject)
      .withMessage(message))
    result.getMessageId
  }
}
