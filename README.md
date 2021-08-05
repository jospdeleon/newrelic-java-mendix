[![New Relic Experimental header](https://github.com/newrelic/opensource-website/raw/master/src/images/categories/Experimental.png)](https://opensource.newrelic.com/oss-category/#new-relic-experimental)

# New Relic Java Instrumentation for Mendix Framework

Provides basic instrumentation for Mendix 8.x.

## Installation

To install the instrumentation:

1. Download the latest release.
2. In the New Relic Java Agent directory, create a directory named extensions if it does not already exist.
3. Copy the downloaded jar file into the extensions directory.
4. Restart the Mendix application server.

## Getting Started

Once installed, the New Relic Java agent will start tracing elements in Mendix that are not captured by the Java agent out of the box. The current implementation names the transactions either by `RequestHandler`, `CoreAction` or `UserAction`. There still may be other Mendix components that need further instrumentation.
## Building

Because Mendix is not an open source framework, it is assumed that you have access to a valid Mendix instance in order to build the instrumentation module. You will need to copy jar files from your Mendix installation to the lib directory of the module (i.e. `mendix-core`) that you want to build. The lib directory contains a `holder.txt` file that contains the list of necessary jar files from your Mendix installation.

If you make changes to the instrumentation code and need to build the instrumentation jar, follow these steps:

1. Set environment variable NEW_RELIC_EXTENSIONS_DIR. Its value should be the directory where you want the jar to be built (i.e. the extensions directory of the Java Agent).
2. Check the lib directory for the list of Mendix jars to include in order to build. The list is in `holder.txt`.
3. Run the command: `./gradlew mendix-core:clean mendix-core:install`

## Support

New Relic has open-sourced this project. This project is provided AS-IS WITHOUT WARRANTY OR DEDICATED SUPPORT. Issues and contributions should be reported to the project here on GitHub.

We encourage you to bring your experiences and questions to the [Explorers Hub](https://discuss.newrelic.com) where our community members collaborate on solutions and new ideas.

## Contributing

We encourage your contributions to improve this project! Keep in mind when you submit your pull request, you'll need to sign the CLA via the click-through using CLA-Assistant. You only have to sign the CLA one time per project. If you have any questions, or to execute our corporate CLA, required if your contribution is on behalf of a company, please drop us an email at opensource@newrelic.com.

**A note about vulnerabilities**

As noted in our [security policy](../../security/policy), New Relic is committed to the privacy and security of our customers and their data. We believe that providing coordinated disclosure by security researchers and engaging with the security community are important means to achieve our security goals.

If you believe you have found a security vulnerability in this project or any of New Relic's products or websites, we welcome and greatly appreciate you reporting it to New Relic through [HackerOne](https://hackerone.com/newrelic).

## License

New Relic Java Instrumentation for Mendix Framework is licensed under the [Apache 2.0](http://apache.org/licenses/LICENSE-2.0.txt) License.