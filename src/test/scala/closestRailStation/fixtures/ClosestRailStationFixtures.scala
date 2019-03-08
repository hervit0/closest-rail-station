package closestRailStation.fixtures
import com.amazonaws.services.lambda.runtime.{ClientContext, CognitoIdentity, Context, LambdaLogger}

object ClosestRailStationFixtures {

  val amazonContext: Context = new Context {
    def getFunctionName: String = "authenticate"

    def getRemainingTimeInMillis: Int = 453

    def getLogger: LambdaLogger = ???

    def getFunctionVersion: String = "3"

    def getMemoryLimitInMB: Int = 256

    def getClientContext: ClientContext = ???

    def getLogStreamName: String = ""

    def getInvokedFunctionArn: String = ""

    def getIdentity: CognitoIdentity = ???

    def getLogGroupName: String = ""

    def getAwsRequestId: String = ""
  }

}
