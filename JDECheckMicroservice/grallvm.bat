REM https://docs.oracle.com/en/graalvm/enterprise/20/docs/docs/getting-started/
REM https://github.com/graalvm/graalvm-ce-builds/releases
REM https://docs.oracle.com/en/graalvm/enterprise/19/index.html
REM https://www.oracle.com/downloads/graalvm-downloads.html
set GRAALVM_HOME=C:\graalvm-ce-java8-20.3.2
set JAVA_HOME=C:\graalvm-ce-java8-20.3.2
set Path=%GRAALVM_HOME%\bin;%Path%
echo GralVm done
"C:\Program Files (x86)\Microsoft Visual Studio\2017\BuildTools\VC\Auxiliary\Build\vcvars64.bat"
native-image --no-fallback -cp ./target/jd-create-ini-files-1.0.0-runner.jar -H:Name=helloworld -H:Class=com.atina.builder.MainBuilder -H:+ReportUnsupportedElementsAtRuntime