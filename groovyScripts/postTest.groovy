//  @author tarun.murala

import groovy.json.*
import jenkins.model.*;
import hudson.model.*;
import hudson.tasks.*;
import hudson.plugins.git.*;

// manager.listener.logger.println "Into Post Test Script"

def SNOW_URL = "http://localhost:8080/api/now/test_integration_api"
def SNOW_USER_NAME = "admin"
def SNOW_USER_PASS = "admin"


def json = new groovy.json.JsonBuilder()
def build = manager.build
def testResultAction = build.testResultAction
def jsonObj = json.build {
    build_name build.project.getName()
    build_number build.number
    build_url build.url
    build_source "JENKINS"
    branch_name build.project.scm.branches[0].name
}
if (null != testResultAction) {
    jsonObj.build.total_count = testResultAction.totalCount
    jsonObj.build.failed_count = testResultAction.failCount
    jsonObj.build.skipped_count = testResultAction.skipCount
    jsonObj.build.passed_count = total - failed - skipped
}
def testAction = build.getAction(hudson.tasks.junit.TestResultAction.class);
if(null != testAction) {
    jsonObj.build.passed build.getAction(hudson.tasks.junit.TestResultAction.class).getPassedTests()
    jsonObj.build.failed build.getAction(hudson.tasks.junit.TestResultAction.class).getFailedTests()    
}
// manager.listener.logger.println jsonObj.toString()

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
// manager.listener.logger.println(postRC);
// if(postRC.equals(200)) {
//     // manager.listener.logger.println(post.getInputStream().getText());
// }