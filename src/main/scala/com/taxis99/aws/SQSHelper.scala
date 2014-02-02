package com.taxis99.aws

import scala.collection.JavaConverters._

import com.amazonaws.services.sqs.AmazonSQSClient
import com.amazonaws.services.sqs.AmazonSQSAsyncClient
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.sqs.model._

/**
 * Helper to handle SQS Interface ( lots of uggly Java Api )
 */
class SQSHelper(accessKey: String, secretKey: String, queueName: String, endpoint: String) {
  
  private val client = new AmazonSQSAsyncClient(new BasicAWSCredentials(accessKey, secretKey))
  client.setEndpoint(endpoint)

  private lazy val queueUrl = client.createQueue(new CreateQueueRequest(queueName)).getQueueUrl

  def fetchMessage = {
    val request = new ReceiveMessageRequest(queueUrl).withMaxNumberOfMessages(1).withAttributeNames("ApproximateReceiveCount")
    client.receiveMessage(request).getMessages().asScala.toList.headOption
  }

  def deleteMessage(message: Message) = {
    if (message != null) {
      client.deleteMessage(new DeleteMessageRequest(queueUrl, message.getReceiptHandle))
    }
  }

  def send(body: String) = {
    client.sendMessage(new SendMessageRequest(queueUrl, body))
  }

}
