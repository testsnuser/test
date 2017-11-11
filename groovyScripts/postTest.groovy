//  @author tarun.murala

import groovy.json.*

manager.listener.logger.println "Into Post Test Script"

def SNOW_URL = "http://localhost:8080/api/now/test_integration_api"
def SNOW_USER_NAME = "admin"
def SNOW_USER_PASS = "admin"

def json = new groovy.json.JsonBuilder()
def testResultAction = manager.build.testResultAction
def total = 0
def failed = 0
def skipped = 0
def passed = 0
if (testResultAction != null) {
    total = testResultAction.totalCount
    failed = testResultAction.failCount
    skipped = testResultAction.skipCount
    passed = total - failed - skipped
}
def jsonObj = json.build {
    build_name manager.build.project.getName()
    build_number manager.build.number
    build_url manager.build.url
    total_tests total
    failed_tests failed
    skipped_tests skipped
    passed_tests passed_tests 
}

manager.listener.logger.println jsonObj.toString()

def post = new URL(SNOW_URL).openConnection();
def userpass = SNOW_USER_NAME + ":" + SNOW_USER_PASS;
String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
post.setRequestProperty ("Authorization", basicAuth);

def message = jsonObj.toString()
post.setRequestMethod("POST")
post.setDoOutput(true)
post.setRequestProperty("Content-Type", "application/json")
post.getOutputStream().write(message.getBytes("UTF-8"));
def postRC = post.getResponseCode();
manager.listener.logger.println(postRC);
if(postRC.equals(200)) {
    manager.listener.logger.println(post.getInputStream().getText());
}