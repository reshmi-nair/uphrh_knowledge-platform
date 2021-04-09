package controllers.v4

import akka.actor.{ActorRef, ActorSystem}
import controllers.BaseController
import javax.inject.{Inject, Named}
import play.api.mvc.ControllerComponents
import utils.{ActorNames, ApiId, QuestionOperations}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext

class QuestionController @Inject()(@Named(ActorNames.QUESTION_ACTOR) questionActor: ActorRef, cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends BaseController(cc) {

	val objectType = "Question"
	val schemaName: String = "question"
	val version = "1.0"

	def create() = Action.async { implicit request =>
		val headers = commonHeaders()
		val body = requestBody()
		val question = body.getOrDefault("question", new java.util.HashMap()).asInstanceOf[java.util.Map[String, AnyRef]]
		question.putAll(headers)
		val questionRequest = getRequest(question, headers, QuestionOperations.createQuestion.toString)
		setRequestContext(questionRequest, version, objectType, schemaName)
		getResult(ApiId.CREATE_QUESTION, questionActor, questionRequest)
	}

	def read(identifier: String, mode: Option[String], fields: Option[String]) = Action.async { implicit request =>
		val headers = commonHeaders()
		val question = new java.util.HashMap().asInstanceOf[java.util.Map[String, Object]]
		question.putAll(headers)
		question.putAll(Map("identifier" -> identifier, "fields" -> fields.getOrElse(""), "mode" -> mode.getOrElse("read")).asJava)
		val questionRequest = getRequest(question, headers, QuestionOperations.readQuestion.toString)
		setRequestContext(questionRequest, version, objectType, schemaName)
		getResult(ApiId.READ_QUESTION, questionActor, questionRequest)
	}

	def update(identifier: String) = Action.async { implicit request =>
		val headers = commonHeaders()
		val body = requestBody()
		val question = body.getOrDefault("question", new java.util.HashMap()).asInstanceOf[java.util.Map[String, Object]];
		question.putAll(headers)
		val questionRequest = getRequest(question, headers, QuestionOperations.updateQuestion.toString)
		setRequestContext(questionRequest, version, objectType, schemaName)
		questionRequest.getContext.put("identifier", identifier)
		getResult(ApiId.UPDATE_QUESTION, questionActor, questionRequest)
	}

	def review(identifier: String) = Action.async { implicit request =>
		val headers = commonHeaders()
		val body = requestBody()
		val question = body.getOrDefault("question", new java.util.HashMap()).asInstanceOf[java.util.Map[String, Object]];
		question.putAll(headers)
		val questionRequest = getRequest(question, headers, QuestionOperations.reviewQuestion.toString)
		setRequestContext(questionRequest, version, objectType, schemaName)
		questionRequest.getContext.put("identifier", identifier)
		getResult(ApiId.REVIEW_QUESTION, questionActor, questionRequest)
	}

	def publish(identifier: String) = Action.async { implicit request =>
		val headers = commonHeaders()
		val body = requestBody()
		val question = body.getOrDefault("question", new java.util.HashMap()).asInstanceOf[java.util.Map[String, Object]];
		question.putAll(headers)
		val questionRequest = getRequest(question, headers, QuestionOperations.publishQuestion.toString)
		setRequestContext(questionRequest, version, objectType, schemaName)
		questionRequest.getContext.put("identifier", identifier)
		getResult(ApiId.PUBLISH_QUESTION, questionActor, questionRequest)
	}

	def retire(identifier: String) = Action.async { implicit request =>
		val headers = commonHeaders()
		val question = new java.util.HashMap().asInstanceOf[java.util.Map[String, Object]]
		question.putAll(headers)
		val questionRequest = getRequest(question, headers, QuestionOperations.retireQuestion.toString)
		setRequestContext(questionRequest, version, objectType, schemaName)
		questionRequest.getContext.put("identifier", identifier)
		getResult(ApiId.RETIRE_QUESTION, questionActor, questionRequest)
	}
}