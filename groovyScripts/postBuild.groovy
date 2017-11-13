//  @author tarun.murala

import groovy.json.*;
import jenkins.model.*;
import hudson.model.*;
import hudson.tasks.*;
import hudson.plugins.git.*;

//  manager.listener.logger.println "Into Post Build Script"

def SNOW_URL = "http://localhost:8080/api/now/build_integration_api"
def SNOW_USER_NAME = "admin"
def SNOW_USER_PASS = "admin"
def INCLUDE_LOG = true

def json = new groovy.json.JsonBuilder()
def result = manager.build.result
def buildLog = manager.build.logFile.text
def jsonObj = json.build {
    build_name manager.build.project.getName()
    build_number manager.build.number
    build_url manager.build.url
    build_result result.toString()
    build_source "JENKINS"
    branch_name manager.build.project.scm.branches[0].name
}

if (INCLUDE_LOG) { // better way to handle the encoding/ escaping
    jsonObj.build.log = buildLog.bytes.encodeBase64().toString()
}

//  manager.listener.logger.println jsonObj.toString()

def post = new URL(SNOW_URL).openConnection();
def userpass = SNOW_USER_NAME + ":" + SNOW_USER_PASS;
String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
post.setRequestProperty ("Authorization", basicAuth);

def message = new JsonBuilder(jsonObj).toPrettyString()
post.setRequestMethod("POST")
post.setDoOutput(true)
post.setRequestProperty("Content-Type", "application/json")
post.getOutputStream().write(message.getBytes("UTF-8"));
def postRC = post.getResponseCode();
//  manager.listener.logger.println(postRC);
//  if(postRC.equals(200)) {
    //  manager.listener.logger.println(post.getInputStream().getText());
// }