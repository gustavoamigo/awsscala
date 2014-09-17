package com.taxis99.aws

import scala.collection.JavaConverters._

import com.amazonaws.services.sqs.AmazonSQSClient
import com.amazonaws.services.sqs.AmazonSQSAsyncClient
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.sqs.model._

/**
 * Helper to handle SQS Interface
 */
class SQSHelper(accessKey: String, secretKey: String, queueName: String, endpoint: String) {
  
  private lazy val (client, queueUrl) = {
    val newClient = new AmazonSQSClient(new BasicAWSCredentials(accessKey, secretKey))
    newClient.setEndpoint(endpoint)
    val newQueueUrl = newClient.createQueue(new CreateQueueRequest(queueName)).getQueueUrl
    (newClient, newQueueUrl)
  }

  def fetchMessages(maxNumberOfMessages: Int = 1): List[Message] = {
    val request = (new ReceiveMessageRequest(queueUrl)).withMaxNumberOfMessages(maxNumberOfMessages).withAttributeNames("ApproximateReceiveCount", "SentTimestamp")
    client.receiveMessage(request).getMessages().asScala.toList
  }

  def fetchApproximatlyAllMessages(numberOfMessages: Int = 10): List[Message] = {
    fetchMessages(approximateNumberOfMessages + numberOfMessages)
  }

  def deleteMessage(message: Message): Unit = {
    if (message != null) {
      client.deleteMessage(new DeleteMessageRequest(queueUrl, message.getReceiptHandle))
    }
  }

  def send(body: String): Unit = {
    client.sendMessage(new SendMessageRequest(queueUrl, body))
  }

  def approximateNumberOfMessages(): Integer = {
    client.getQueueAttributes(new GetQueueAttributesRequest(queueUrl, List("ApproximateNumberOfMessages").asJava))
      .getAttributes.get("ApproximateNumberOfMessages").toInt
  }

}
